import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class Payment extends Application {
	final TextField prin = new TextField();
	final TextField intR = new TextField();
	final TextField years = new TextField();
	final Text results = new Text();
	@Override
	public void start(Stage st){
		GridPane data = new GridPane();
		BorderPane mainPane = new BorderPane();
		Scene sc = new Scene(mainPane, 500, 500);
		st.setScene(sc);
		Font f1 = Font.font("san-serif", FontWeight.BOLD, FontPosture.REGULAR, 20);
		Font f2 = Font.font("times", FontWeight.NORMAL, FontPosture.REGULAR, 12);
		Label principal = new Label("Enter the principal amount: ");
		Label rate = new Label("Enter the interest rate: ");
		Label term = new Label("Enter the payment term: ");
		Button calc = new Button("Calculate Payment");
		calc.setOnAction(new Calculate());
		calc.setFont(f1);
		prin.setFont(f2);
		intR.setFont(f2);
		years.setFont(f2);
		principal.setFont(f1);
		rate.setFont(f1);
		term.setFont(f1);
		data.add(principal, 0, 0);
		data.add(prin, 1, 0);
		data.add(rate, 0, 1);
		data.add(intR, 1, 1);
		data.add(term, 0, 2);
		data.add(years, 1, 2);
		data.add(calc, 1, 3);
		data.setPadding(new Insets(10, 10, 10, 10));
		data.setHgap(5);
		data.setVgap(5);
		results.setFont(f1);
		mainPane.setTop(data);
		mainPane.setCenter(results);
		st.show();
	}
    
	public static void main(String[] args){
		launch(args);
	}
	
	class Calculate implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {
			double p = 0;
			double r = 0;
			int t = 0;
			try{
				PrintWriter op = new PrintWriter(new File("results.txt"));
				p = Double.parseDouble(prin.getText());
				r = Double.parseDouble(intR.getText());
				t = Integer.parseInt(years.getText());
				int n = t * 12;
				double j = (r/100)/12;
				double m = p * (j/(1-Math.pow((1 + j), -n)));
				results.setText("Your monthly payment will be $" + String.format("%.2f", m));
				for (int i = 1; i <= n; i++){
					double interest = ((r/100)/12) * p;
					double payP = m - interest;
					p -= payP;
					op.println("Month " + i + ":");
				    op.println();
					op.printf("Note Principal: $%.2f", payP);
					op.println();
					op.printf("Note Interest: $%.2f", interest);
					op.println();
					op.printf("Principal Balance: $%.2f", p);
					op.println();
				}
				op.close();
			}catch(FileNotFoundException e){
				e.getMessage();
			}
		}
		
	}
}


