package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberService {
    public List<Member> list() {
        // db 접속을 위한 5개 정보
        // ip(domain), port, 계정, 패스워드, 인스턴스
    	
    	List<Member> memberList = new ArrayList<>();
    	
        String url = "jdbc:mariadb://localhost:3306/testdb1";
        String dbUserId = "testuser1";
        String dbPassWord = "!@aud221166";

        //1. 드라이버로드
        //  mariadb-java-client>Driver의 패키지.Driver
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        String memberTypeValue = "email";

        try {
            //2. 커넥션 객체 생성
            connection = DriverManager.getConnection(url, dbUserId, dbPassWord);

            //4. 쿼리 실행
            String sql = "SELECT\n" +
                    "\tmember_type,\n" +
                    "\tuser_id,\n" +
                    "\tpassword,\n" +
                    "\tname\n" +
                    "FROM\n" +
                    "\tMEMBER\n" +
                    "WHERE\n" +
                    "\tmember_type = ?\n" +
                    "; ";

            //3. 스테이트먼트 객체 생성
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, memberTypeValue);

            rs = preparedStatement.executeQuery();

            //5. 결과 수행
            while (rs.next()) {
                String memberType = rs.getString("member_type");
                String userId = rs.getString("user_id");
                String password = rs.getString("password");
                String name = rs.getString("name");
                
                Member member = new Member();
                member.setMemberType(memberType);
                member.setUserId(userId);
                member.setPassword(password);
                member.setName(name);
                
                memberList.add(member);

                System.out.printf("%s, %s, %s, %s\n", memberType, userId, password, name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            //6. 객체 연결 해제(close)
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        
        return memberList;
    }

    public Member detail(String memberType, String userId) {
        // db 접속을 위한 5개 정보
        // ip(domain), port, 계정, 패스워드, 인스턴스
    	
    	Member member = null;
    	
        String url = "jdbc:mariadb://localhost:3306/testdb1";
        String dbUserId = "testuser1";
        String dbPassWord = "!@aud221166";

        //1. 드라이버로드
        //  mariadb-java-client>Driver의 패키지.Driver
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            //2. 커넥션 객체 생성
            connection = DriverManager.getConnection(url, dbUserId, dbPassWord);

            //4. 쿼리 실행
            String sql = "SELECT\r\n"
            		+ "	m.member_type,\r\n"
            		+ "	m.user_id,\r\n"
            		+ "	m.password,\r\n"
            		+ "	m.name,\r\n"
            		+ "	md.mobile_no,\r\n"
            		+ "	md.marketing_yn,\r\n"
            		+ "	md.register_date \r\n"
            		+ "FROM\r\n"
            		+ "	MEMBER m\r\n"
            		+ "	LEFT JOIN member_detail md ON md.member_type = m.member_type AND md.user_id = m.user_id \r\n"
            		+ "WHERE\r\n"
            		+ "	m.member_type = ? AND m.user_id = ?\r\n"
            		+ ";";

            //3. 스테이트먼트 객체 생성
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, memberType);
            preparedStatement.setString(2, userId);

            rs = preparedStatement.executeQuery();

            //5. 결과 수행
            if (rs.next()) {
            	member = new Member();
                member.setMemberType(rs.getString("member_type"));
                member.setUserId(rs.getString("user_id"));
                member.setPassword(rs.getString("password"));
                member.setName(rs.getString("name"));
                member.setMobileNo(rs.getString("mobile_no"));
                member.setMarketingYn(rs.getBoolean("marketing_yn"));
                member.setRegisterDate(rs.getDate("register_date"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            //6. 객체 연결 해제(close)
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        
        return member;
    }

    
    /**
     * 회원 가입
     * @param member 회원정보
     */
    public void register(Member member) {
        // db 접속을 위한 5개 정보
        // ip(domain), port, 계정, 패스워드, 인스턴스

        String url = "jdbc:mariadb://localhost:3306/testdb1";
        String dbUserId = "testuser1";
        String dbPassWord = "!@aud221166";

        //1. 드라이버로드
        //  mariadb-java-client>Driver의 패키지.Driver
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            //2. 커넥션 객체 생성
            connection = DriverManager.getConnection(url, dbUserId, dbPassWord);

            //4. 쿼리 실행
            String sql = "INSERT INTO `member` (member_type, user_id, password, name)\n" +
                    "VALUES (?, ?, ?, ?)\n" +
                    ";";

            //3. 스테이트먼트 객체 생성
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, member.getMemberType());
            preparedStatement.setString(2, member.getUserId());
            preparedStatement.setString(3, member.getPassword());
            preparedStatement.setString(4, member.getName());

            int affectedRows = preparedStatement.executeUpdate();

            //5. 결과 수행
            if (affectedRows > 0) {
                System.out.println("저장 성공");
            } else {
                System.out.println("저장 실패");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            //6. 객체 연결 해제(close)
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void dbUpdate() {
        // db 접속을 위한 5개 정보
        // ip(domain), port, 계정, 패스워드, 인스턴스

        String url = "jdbc:mariadb://localhost:3306/testdb1";
        String dbUserId = "testuser1";
        String dbPassWord = "!@aud221166";

        //1. 드라이버로드
        //  mariadb-java-client>Driver의 패키지.Driver
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        String memberTypeValue = "email";
        String userIdValue = "zerobase@naver.com";
        String passwordValue = "9999";

        try {
            //2. 커넥션 객체 생성
            connection = DriverManager.getConnection(url, dbUserId, dbPassWord);

            //4. 쿼리 실행
            String sql = "UPDATE `member` \n" +
                    "SET\n" +
                    "\tpassword = ?\n" +
                    "WHERE member_type = ?\n" +
                    "\tAND user_id = ?\n" +
                    ";";

            //3. 스테이트먼트 객체 생성
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, passwordValue);
            preparedStatement.setString(2, memberTypeValue);
            preparedStatement.setString(3, userIdValue);

            int affectedRows = preparedStatement.executeUpdate();

            //5. 결과 수행
            if (affectedRows > 0) {
                System.out.println("수정 성공");
            } else {
                System.out.println("수정 실패");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            //6. 객체 연결 해제(close)
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 회원 탈퇴
     */
    public void withdraw(Member member) {
        // db 접속을 위한 5개 정보
        // ip(domain), port, 계정, 패스워드, 인스턴스

        String url = "jdbc:mariadb://localhost:3306/testdb1";
        String dbUserId = "testuser1";
        String dbPassWord = "!@aud221166";

        //1. 드라이버로드
        //  mariadb-java-client>Driver의 패키지.Driver
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            //2. 커넥션 객체 생성
            connection = DriverManager.getConnection(url, dbUserId, dbPassWord);

            //4. 쿼리 실행
            String sql = "DELETE \n" +
                    "FROM `member` \n" +
                    "WHERE member_type = ? AND user_id = ?\n" +
                    ";";

            //3. 스테이트먼트 객체 생성
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, member.getMemberType());
            preparedStatement.setString(2, member.getUserId());

            int affectedRows = preparedStatement.executeUpdate();

            //5. 결과 수행
            if (affectedRows > 0) {
                System.out.println("삭제 성공");
            } else {
                System.out.println("삭제 실패");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            //6. 객체 연결 해제(close)
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
