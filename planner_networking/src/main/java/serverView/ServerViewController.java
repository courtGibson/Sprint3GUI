package serverView;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import loginView.LoginViewController;
import software_masters.planner_networking.Client;
import software_masters.planner_networking.Main;
import software_masters.planner_networking.Server;
import software_masters.planner_networking.ServerImplementation;

public class ServerViewController
{
	
	Stage primaryStage;
	BorderPane mainView;
	Client testClient;
	
	
	public Client getTestClient()
	{
	
		return testClient;
	}

	public void setTestClient(Client testClient)
	{
	
		this.testClient = testClient;
	}

	public Stage getPrimaryStage()
	{
	
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage)
	{
	
		this.primaryStage = primaryStage;
	}

	public BorderPane getMainView()
	{
	
		return mainView;
	}

	public void setMainView(BorderPane mainView)
	{
	
		this.mainView = mainView;
	}

	
	
	
	
	@FXML
	private RadioButton DefaultServerButton;
	
	@FXML
	private RadioButton OtherServerButton;
	
	@FXML 
	private TextField OtherServerTextField;
	
	@FXML
	private Button ServerSubmitButton;
	
	static Server testServer;
	
	static Server actualServer;
	static Registry registry;
	
	
	
	public void connectToServer() {
		if(DefaultServerButton.isSelected()) {
			try
			{
				registry = LocateRegistry.createRegistry(1075);
				ServerImplementation server = new ServerImplementation();
				actualServer = server;
				Server stub = (Server) UnicastRemoteObject.exportObject(server, 0);
				registry.rebind("PlannerServer", stub);
				this.testServer = (Server) registry.lookup("PlannerServer");
				this.testClient = new Client(testServer);
				getConnected(testClient);
				
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else {
			
			String hostName = OtherServerTextField.getAccessibleText();
			try
			{
				registry = LocateRegistry.getRegistry(hostName, 1075);
				this.testServer = (Server) registry.lookup("PlannerServer");
				this.testClient = new Client(testServer);
				getConnected(testClient);
				
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	private void getConnected(Client testClient) throws Exception
	{

		this.testClient = testClient;
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/loginView/loginView.fxml"));
		this.mainView = loader.load();
		
		LoginViewController cont = loader.getController();
		cont.setTestClient(testClient);
		
		primaryStage.getScene().setRoot(mainView);
		
		
		
		
	}
}
