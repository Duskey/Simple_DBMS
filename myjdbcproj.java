//Rohit Kumar
//1741012056
import java.sql.*;
import java.util.*;
public class myjdbcproj
{
	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		Connection con=null;
		PreparedStatement pstmt =null,pstmt2=null;
		ResultSet rs=null;
		int affect=0;
			
		try{				
			Class.forName("oracle.jdbc.driver.OracleDriver");				
			String conurl="jdbc:oracle:thin:@172.17.144.110:1521:ora11g";
			con=DriverManager.getConnection(conurl,"csef1741012056","csef1741012056");
			Statement stmt=con.createStatement();

			int choice = 1;
			do
			{
				System.out.println("\n\n***** Banking Management System*****");
				System.out.println("1 -> Display Customer Records\n2 -> Add Customer Record\n3 -> Delete Customer Record");
				System.out.println("4 -> Update Customer Records\n5 -> Display Account Details\n6 -> Display Loan Details");
				System.out.println("7 -> Deposit Money\n8 -> Withdraw Money\n9 -> Exit");
				System.out.println("Enter your choice(1-9):");
				choice = sc.nextInt();
				String cnum="",name="",city="";
				Long phone;
				switch(choice)
				{
					case 1:
						int a=0;
						// Display customer records
						//Statement stmt=con.createStatement();						
						rs=stmt.executeQuery("select * from CUSTOMER order by cust_no");
						while(rs.next())
						{
							a++;
							System.out.println(rs.getString(1)+ " " + rs.getString(2) + " " +rs.getLong(3) +" "+rs.getString(4));
						}
						System.out.println("Number of rows selected = "+a);
						break;
					case 2:
						// Add customer record
						// Accept input for each column from userbreak;
						System.out.println("enter the customer num, name, phone no. and city of the customer");
						cnum = sc.next();
						sc.nextLine();
						name = sc.nextLine();
						phone = sc.nextLong();
						city = sc.next();						
						pstmt = con.prepareStatement("INSERT INTO CUSTOMER VALUES(?,?,?,?)");
						pstmt.setString(1,cnum);
						pstmt.setString(2,name);
						pstmt.setLong(3,phone);
						pstmt.setString(4,city);
						pstmt.executeUpdate();
						System.out.println("Customer Record Added Successfully");
						break;
					case 3:
						// Delete customer record
						// Accept customer number from user
						System.out.println("enter the customer number to delete");
						cnum = sc.next();
						pstmt = con.prepareStatement("DELETE FROM CUSTOMER WHERE CUST_NO=?");
						pstmt.setString(1,cnum);
						affect = pstmt.executeUpdate();
						if(affect == 0)
							System.out.println("Customer number not present in database");
						else
							System.out.println("Deleted Successfully");
						break;
					case 4:
// Update customer record	
						System.out.println("Enter the customer number");
						cnum = sc.next();
						System.out.println("Enter 1: For Name 2: For Phone no 3: For City to update:");
						int ch = sc.nextInt();
						switch(ch)
						{
							case 1:
								sc.nextLine();
								System.out.println("enter new name");
								name = sc.nextLine();
								pstmt = con.prepareStatement("UPDATE CUSTOMER SET NAME=? WHERE CUST_NO =?");
								pstmt.setString(1,name);
								pstmt.setString(2,cnum);
								affect = pstmt.executeUpdate();
								break;
							case 2:
								System.out.println("enter new PHONE NO.");
								phone = sc.nextLong();
								pstmt = con.prepareStatement("UPDATE CUSTOMER SET PHONE_NO=? WHERE CUST_NO =?");
								pstmt.setLong(1,phone);
								pstmt.setString(2,cnum);
								affect = pstmt.executeUpdate();
								break;
							case 3:
								sc.nextLine();
								System.out.println("enter new city");
								city = sc.nextLine();
								pstmt = con.prepareStatement("UPDATE CUSTOMER SET CITY=? WHERE CUST_NO =?");
								pstmt.setString(1,city);
								pstmt.setString(2,cnum);
								affect = pstmt.executeUpdate();
								break;
						}
						if(affect == 0)
							System.out.println("Customer number not present in database");
						else
						{
							String ss="City";
							if(ch == 1)
								ss = "NAME";
							else if(ch == 2)
								ss = "Phone No.";
							System.out.println("Updated " +ss+" Successfully");
						}
						break;
					case 5:
						System.out.println("Enter the customer number");
						cnum = sc.next();
						pstmt = con.prepareStatement("SELECT * FROM CUSTOMER WHERE CUST_NO = ?");
						pstmt.setString(1,cnum);
						rs = pstmt.executeQuery();
						int x = 0;
						while(rs.next())
						{
							x++;
						}
						if(x==0)
						{
							System.out.println("Customer does not exist");
							break;
						}
						pstmt = con.prepareStatement("SELECT * FROM ACCOUNT WHERE ACCOUNT_NO=(SELECT ACCOUNT_NO FROM DEPOSITOR WHERE CUST_NO =?)");
						pstmt.setString(1,cnum);
						rs = pstmt.executeQuery();
						int j=0;
						while(rs.next())
						{
							j++;
							System.out.println(rs.getString(1)+ " " + rs.getString(2) + " " +rs.getLong(3) +" "+rs.getString(4));
						}
						if(j==0)
						{
							System.out.println("Customer does not have an account");
							break;
						}
						pstmt2 = con.prepareStatement("SELECT * FROM BRANCH WHERE BRANCH_CODE=(SELECT BRANCH_CODE FROM ACCOUNT WHERE ACCOUNT_NO =(SELECT ACCOUNT_NO FROM DEPOSITOR WHERE CUST_NO =?))");
						pstmt2.setString(1,cnum);
						rs = pstmt2.executeQuery();
						while(rs.next())
						{
							System.out.println(rs.getString(1)+" "+rs.getString(2) + rs.getString(3));
						}
						break;
					case 6:
// Display loan details
						System.out.println("Enter the customer number");
						cnum = sc.next();
						pstmt = con.prepareStatement("SELECT * FROM CUSTOMER WHERE CUST_NO = ?");
						pstmt.setString(1,cnum);
						rs = pstmt.executeQuery();
						int y = 0;
						while(rs.next())
						{
							y++;
						}
						if(y==0)
						{
							System.out.println("Customer does not exist");
							break;
						}
						pstmt = con.prepareStatement("SELECT * FROM LOAN WHERE CUST_NO =?");
						pstmt.setString(1,cnum);
						rs = pstmt.executeQuery();
						int i =0;
						while(rs.next())
						{
							i++;
							System.out.print(rs.getString(1)+ " " + rs.getString(2) + " " +rs.getLong(3) +" "+rs.getString(4));
						}
						if(i==0)
						{
							System.out.println("Congratulations you don't have any pending loans");
							break;
						}
						pstmt2 = con.prepareStatement("SELECT * FROM BRANCH WHERE BRANCH_CODE=(SELECT BRANCH_CODE FROM LOAN WHERE CUST_NO =?)");
						pstmt2.setString(1,cnum);
						rs = pstmt2.executeQuery();
						while(rs.next())
						{
							System.out.println(rs.getString(1)+" "+rs.getString(2) + rs.getString(3));
						}
						break;
					case 7:
//Deposit money
						System.out.println("Enter the ACCOUNT NUMBER");
						String acc1 = sc.next();
						System.out.println("ENTER THE AMOUNT OF MONEY TO DEPOSIT");
						Long bal = sc.nextLong();
						pstmt = con.prepareStatement("SELECT BALANCE FROM ACCOUNT WHERE ACCOUNT_NO =?");
						pstmt.setString(1,acc1);
						rs = pstmt.executeQuery();
						rs.next();
						bal = bal + rs.getLong(1);
						pstmt = con.prepareStatement("UPDATE ACCOUNT SET BALANCE=? WHERE ACCOUNT_NO=?");
						pstmt.setLong(1,bal);
						pstmt.setString(2,acc1);
						pstmt.executeQuery();
						System.out.println("TRANSACTION COMPLETE");
						pstmt = con.prepareStatement("SELECT BALANCE FROM ACCOUNT WHERE ACCOUNT_NO =?");
						pstmt.setString(1,acc1);
						rs = pstmt.executeQuery();
						while(rs.next())
						{
							System.out.println("Updated balance is ="+rs.getString(1));
						}
// Accept the account number to be deposited in
// Message for transaction completion
						break;
					case 8:
//Withdraw money
						System.out.println("Enter the ACCOUNT NUMBER");
						String acc = sc.next();
						System.out.println("ENTER THE AMOUNT OF MONEY TO WITHDRAW");
						long withdraw = sc.nextLong();
						pstmt = con.prepareStatement("SELECT BALANCE FROM ACCOUNT WHERE ACCOUNT_NO =?");
						pstmt.setString(1,acc);
						rs = pstmt.executeQuery();
						rs.next();
						long balavail = rs.getLong(1);
						if(balavail>withdraw)
						{
							balavail = balavail - withdraw;
							pstmt = con.prepareStatement("UPDATE ACCOUNT SET BALANCE=? WHERE ACCOUNT_NO=?");
							pstmt.setLong(1,balavail);
							pstmt.setString(2,acc);
							pstmt.executeQuery();
							System.out.println("TRANSACTION COMPLETE");
						}
						else System.out.println("NOT ENOUGH BALANCE");
						pstmt = con.prepareStatement("SELECT BALANCE FROM ACCOUNT WHERE ACCOUNT_NO =?");
						pstmt.setString(1,acc);
						rs = pstmt.executeQuery();
						while(rs.next())
						{
							System.out.println("Updated balance is ="+rs.getString(1));
						}
						break;
					case 9:
						break;
					default:
						System.out.println("WRONG CHOICE");
				}
			}while(choice!=9);
		} //try closing
		catch(Exception e)
		{
			System.out.println(e); 
		}
	}
}