package application;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class Main extends Application {
	Compress compress;
	Decompress decompress;
	File file;
	String filePath;
	Label label1 = new Label();
	Label label2 = new Label();
TextArea ta1=new TextArea();
TextArea ta2=new TextArea();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch(args);
	}

	public void start(Stage stage) throws Exception {
		Button bcompress = new Button("Compress");
		Button bdecompress = new Button("Decompress");
		Button fileImport1 = new Button("Choose File");
		Button fileImport2 = new Button("Choose File");
		Button bheader = new Button("Header");
		Button statistics = new Button("Statistics");
		ScrollPane scrl=new ScrollPane();
		Alert alert = new Alert(AlertType.ERROR); // error messages

		bheader.setDisable(true);
		statistics.setDisable(true);

		VBox v = new VBox();
		VBox v1 = new VBox();

		HBox h1 = new HBox(20);
		HBox h2 = new HBox(20);
		HBox h3 = new HBox(20);

		bcompress.setPrefWidth(100);
		bdecompress.setPrefWidth(100);
		fileImport1.setPrefWidth(100);
		fileImport2.setPrefWidth(100);
		bheader.setPrefWidth(100);
		statistics.setPrefWidth(100);
		h1.setAlignment(Pos.CENTER);
		h2.setAlignment(Pos.CENTER);
		h3.setAlignment(Pos.CENTER);

		v.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
				+ "-fx-border-radius: 2.5;" + "-fx-border-color: black;" + "-fx-background-color: white");
		v1.setStyle("-fx-border-style: solid inside;" + "-fx-border-insets: 5;" + "-fx-border-radius: 2.5;"
				+ "-fx-border-color: black;" + "-fx-background-color: white");

		h1.setPadding(new Insets(15, 15, 15, 15));
		h2.setPadding(new Insets(15, 15, 15, 15));
		h3.setPadding(new Insets(15, 15, 15, 15));

		h1.getChildren().addAll(fileImport1, bcompress);
		h2.getChildren().addAll(bheader, statistics);
		h3.getChildren().addAll(fileImport2, bdecompress);
		v1.getChildren().addAll(h1, h2, label1,ta1,ta2);
		v.getChildren().addAll(v1, h3, label2);
		Scene scene = new Scene(v,400,400);
		stage.setScene(scene);
		stage.setTitle("Huffman");
		stage.show();

		fileImport1.setOnAction(e -> {

			bdecompress.setDisable(true);
			bcompress.setDisable(false);
			bheader.setDisable(true);
			statistics.setDisable(true);
		
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Load File");
			file = fileChooser.showOpenDialog(null);
			filePath = file.getPath();
			
		});

		fileImport2.setOnAction(e -> {
			bcompress.setDisable(true);
			bdecompress.setDisable(false);
			bheader.setDisable(true);
			statistics.setDisable(true);
			FileChooser fileChooser = new FileChooser();
           fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("HUF", "*.hfm"));
                 

			fileChooser.setTitle("Load File");
			file = fileChooser.showOpenDialog(null);
			filePath = file.getPath();
			if (!filePath.endsWith(".hfm")) {
				alert.setContentText("You should select files with extention .huf , retry again!");
				alert.showAndWait();
			}
		});

		bcompress.setOnAction(e -> {
			label1.setText("         Compression process...");
			if (file != null) {

				try {
					compress = new Compress(filePath);
					try {
						compress.readFile();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					compress.createHeap();
					compress.writeHuffmanCode();
					label1.setText("         Compression Complete");
					bheader.setDisable(false);
					statistics.setDisable(false);
				
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
				}

			}
		});

		bdecompress.setOnAction(e -> {
			label2.setText("         Decompression process...");
			statistics.setDisable(true);
			bheader.setDisable(true);

			if (filePath.endsWith(".hfm")) {

				decompress = new Decompress(filePath);
				try {
					decompress.readHuffFile();
					label2.setText("         Decompression Complete");
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				alert.setContentText("You should select files with extention .hfm , retry again!");
				alert.showAndWait();
			}
		});

		bheader.setOnAction(e ->{
			Header header=compress.getHeader();
			ta2.setText("File Name: "+header.getFileName()+"\nFile Size: "+header.getFileSize()+"Byte");
			ta2.setText(ta2.getText()+"\n\nByte \t Huffman Code \n");
			Huffman table[] = compress.getHuffTable();
			for (int i =0 ; i <table.length ; i++)
				ta2.setText(ta2.getText() + table[i].byteCount + " \t " +table[i].Huffman + "\n");
		
		});
		
		statistics.setOnAction(e ->{
			Header header = compress.getHeader();
			ta1.setText("File Name: " + header.getFileName()
					+ "\nOrginal File Size: " + header.getFileSize() + "\n "
					+ "\nCompressed File Size: " + header.getBytes().length + "\n "
					+ "\nCompression Ratio: " + compress.getRatio() + "%\n "
					+ "\nOrginal File Bytes: " + header.getFileSize() + "\n "

					+ "Byte " + "  " + "Count \n" );
			//view on screen 
			for (int i=0 ; i<header.getBytes().length ; i++)
				ta1.setText(ta1.getText() + header.getBytes()[i] 
						+ "  " + header.getCount()[i] + "\n");
	
		});
		
	
	}
}
