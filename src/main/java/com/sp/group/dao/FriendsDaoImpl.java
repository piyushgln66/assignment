package com.sp.group.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.sp.group.entity.Blocker;
import com.sp.group.entity.Friends;
import com.sp.group.entity.Subscriber;

@Service
public class FriendsDaoImpl implements FriendsDao {
	
	private static final Logger log = LoggerFactory.getLogger(FriendsDaoImpl.class);
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Override
	public void initTables() {
		log.info("Creating friends table");

        jdbcTemplate.execute("DROP TABLE friends IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE friends(" +
                "first_email VARCHAR(255), second_email VARCHAR(255))");
        
        
        log.info("Creating subscribe table");

        jdbcTemplate.execute("DROP TABLE subscribe IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE subscribe(" +
                "requestor VARCHAR(255), target VARCHAR(255), "
                + "CONSTRAINT PK_Subscribe PRIMARY KEY (requestor,target))");
        
        log.info("Creating block table");

        jdbcTemplate.execute("DROP TABLE block IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE block(" +
                "requestor VARCHAR(255), target VARCHAR(255), "
                + "CONSTRAINT PK_Block PRIMARY KEY (requestor,target))");
        return ;
	}
	
	private boolean isFriend(String first_email, String second_email) {
		List<Friends> friendsList = jdbcTemplate.query("SELECT * FROM friends WHERE FIRST_EMAIL='"+
				first_email+"' AND SECOND_EMAIL='"+second_email+"'",
				new BeanPropertyRowMapper(Friends.class));
		if(friendsList.size()>0) {
			log.info("these people  are already connected as friends");
			return true;
		}
		return false;
	}

	@Override
	public void connect(String first_email, String second_email) {
		if(!isFriend(first_email, second_email) && !isAlreadyBlocked(first_email, second_email)
				&& !isAlreadyBlocked(second_email, first_email)) {
			log.info("creating a new friends connection between these two people");
			jdbcTemplate.update("INSERT INTO friends(first_email, second_email) VALUES (?,?)", 
					first_email, second_email);
			jdbcTemplate.update("INSERT INTO friends(first_email, second_email) VALUES (?,?)", 
					second_email, first_email);
			//once 2 people become friends they subscribe to each other's updates
			subscribe(first_email, second_email);
			subscribe(second_email, first_email);
		}
		return ;
	}

	@Override
	public List<String> getFriendsList(String email) {
		List<String> friendsList = jdbcTemplate.query("SELECT SECOND_EMAIL FROM friends WHERE FIRST_EMAIL='"+
				email+"'", new RowMapper<String>(){
			            public String mapRow(ResultSet rs, int rowNum) 
			                                         throws SQLException {
			                    return rs.getString(1);
			            }
       });
	   return friendsList;
	}

	@Override
	public List<String> getMutualFriends(String first_email, String second_email) {
		List<String> friendsList = jdbcTemplate.query("SELECT SECOND_EMAIL FROM friends WHERE FIRST_EMAIL='"+
				first_email+"' AND SECOND_EMAIL IN "+
	            "(SELECT SECOND_EMAIL FROM friends WHERE FIRST_EMAIL='"+
				 second_email+"')", new RowMapper<String>(){
			            public String mapRow(ResultSet rs, int rowNum) 
			                                         throws SQLException {
			                    return rs.getString(1);
			            }
       });
	   return friendsList;
	}
	
	private boolean isAlreadySubscribed(String requestor, String target) {
		List<Subscriber> list = jdbcTemplate.query("SELECT * FROM subscribe WHERE requestor='"+
				requestor+"' AND target='"+target+"'",
				new BeanPropertyRowMapper(Subscriber.class));
		if(list.size()>0) {
			log.info("this subscription is already active");
			return true;
		}
		return false;
	}

	@Override
	public void subscribe(String requestor, String target) {
		if(!isAlreadySubscribed(requestor,target)) {
			jdbcTemplate.update("INSERT INTO subscribe(requestor, target) VALUES (?,?)", 
					requestor, target);
		}
		return ;
	}
	
	private void unSubscribe(String requestor, String target) {
		if(isAlreadySubscribed(requestor,target)) {
			jdbcTemplate.update("DELETE FROM subscribe WHERE requestor='"+
					requestor+"' AND target='"+target+"'");
		}
		return ;
	}
	
	private boolean isAlreadyBlocked(String requestor, String target) {
		List<Blocker> list = jdbcTemplate.query("SELECT * FROM block WHERE requestor='"+
				requestor+"' AND target='"+target+"'",
				new BeanPropertyRowMapper(Blocker.class));
		if(list.size()>0) {
			log.info("the target is already blocked by the requestor");
			return true;
		}
		return false;
	}

	@Override
	public void block(String requestor, String target) {
		if(!isAlreadyBlocked(requestor, target)) {
			jdbcTemplate.update("INSERT INTO block(requestor, target) VALUES (?,?)", 
					requestor, target);
			unSubscribe(requestor, target);
		}
		return ;
	}

	@Override
	public List<String> getUpdateRecipients(String sender_email) {
		List<String> recipientsList = jdbcTemplate.query("SELECT requestor FROM subscribe WHERE target='"+
				sender_email+"'", new RowMapper<String>(){
			            public String mapRow(ResultSet rs, int rowNum) 
			                                         throws SQLException {
			                    return rs.getString(1);
			            }
       });
	   return recipientsList;
	}

	@Override
	public List<String> filterBlockingRecipients(List<String> emails, String sender_email) {
		// remove the recipients who have blocked the sender
		List<String> finalList = new ArrayList<String>();
		for(String email : emails) {
			if(!isAlreadyBlocked(email, sender_email)) {
				finalList.add(email);
			}
		}
		return finalList;
	}

}
