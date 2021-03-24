package com.hust.soict.group3T.client_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	
	public static void main(String[] args) {

		String hostname = "127.0.0.1";
		int port = 9898;
		
		try (Socket socket = new Socket(hostname, port)) {
			 
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            BufferedReader in = new BufferedReader(new
            				InputStreamReader(socket.getInputStream()));
            System.out.println(in.readLine());
            
            Scanner scanner = new Scanner(System.in);
            String message;
 
            do {
            	System.out.println("Enter a sequence of numbers (delimeter by space \" \"): ");
                message = scanner.nextLine();
 
                writer.println(message);
 
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
                String result = reader.readLine();
 
                System.out.println(result);
 
            } while (!message.equals(""));
            
            scanner.close();
            socket.close();
 
        } catch (UnknownHostException ex) {
 
            System.out.println("Server not found: " + ex.getMessage());
 
        } catch (IOException ex) {
 
            System.out.println("I/O error: " + ex.getMessage());
        }
	}
}
