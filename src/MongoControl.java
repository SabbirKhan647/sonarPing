import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.JOptionPane;

public class MongoControl {
	private MongoView theView;
	private MongoModel theModel;
	
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private String type;
	
	public MongoControl(MongoView theView, MongoModel theModel)
	{
		this.theView = theView;
		this.theModel = theModel;
		
		this.theView.addMongoButtonListener(new MongoButtonListener());		
	}
	
	public Boolean validate()
	{
		Boolean rt=false;
		//String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\ @([\\w]+\\.)+[\\w]+[\\w]$";
		
		
		if( theView.firstName.getText().isEmpty() ) 
		{
			JOptionPane.showMessageDialog(null,"You need to input first name!");
			rt = false;
		}
		else if( theView.lastName.getText().isEmpty() ) 
		{
			JOptionPane.showMessageDialog(null,"You need to input last name!");
			rt = false;
		}
		else if( theView.password.getText().isEmpty() ) 
		{
			JOptionPane.showMessageDialog(null,"You need to input password!");
			rt = false;
		}
		else if( theView.email.getText().isEmpty() ) 
		{
			JOptionPane.showMessageDialog(null,"You need to input email!");
			rt = false;
		}
		else if(!theView.email.getText().isEmpty())
		{
			try {
				InternetAddress emailAddr = new InternetAddress(theView.email.getText());
				emailAddr.validate();
			} catch (AddressException ex) {
				JOptionPane.showMessageDialog(null,"You need to input a correct email!");
				rt = false;
			}
			//return result;
		}
		
		else if( theView.type.getText().isEmpty() ) 
		{
			JOptionPane.showMessageDialog(null,"You need to input type!");
			rt = false;
		}
		else if ( !theView.type.getText().equals("DEPENDENT") )
		{
			JOptionPane.showMessageDialog(null,"You need to input type to be DEPENDENT!");
			rt = false;
		}
		else
		{
			rt = true;
		}
		return rt;
	}
	
	public void clearFields(){
		theView.firstName.setText("");
		theView.lastName.setText("");
		theView.password.setText("");
		theView.email.setText("");
		theView.type.setText("");
	}
	
	class MongoButtonListener implements ActionListener
	{
		
		public void actionPerformed(ActionEvent e) 
		{
			String str;
			int dialogreturn;
			String key, value;
			Boolean ret;
			
			firstName = theView.getFirstName();
			lastName = theView.getLastName();
			password = theView.getPassword();
			email = theView.getEmail();
			type = theView.getUserType();
						
			try
			{
				if(e.getActionCommand().equals("CreateButton"))
				{
					ret = validate();
					if(ret)
						theModel.insertMongoDB(firstName,lastName,email,password,type);
				}
				else if(e.getActionCommand().equals("deleteButton"))
				{
					key = theView.getFirstName();
					theModel.deleteMongoDB(key);
				}
				else if(e.getActionCommand().equals("updateButton"))
				{
					theModel.updateMongoDB(firstName, lastName, email, password,type);				
				}
				else if(e.getActionCommand().equals("browseButton"))
				{
					str = theModel.browseMongoDB();
					//JOptionPane.showMessageDialog(null,str);
				}	
				else if(e.getActionCommand().equals("removeallButton"))
				{
					dialogreturn = theModel.removeallMongo();
					JOptionPane.showMessageDialog(null,"Remove all the data in MongoDB");	
				}				
				
				
				clearFields();
			}
			catch(Exception ex)
			{
				theView.displayErrorMessage("Mongo DB operation fail.");;
			}
		}
	}
}