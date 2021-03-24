package com.hust.soict.group3T.client_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import com.hust.soict.group3T.helper.*;
import java.util.Arrays;
	
public class Server {
	private static int port = 9898;
	public static void main(String[] args) throws IOException {
		int clientNumber = 0;
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			 
			System.out.println("The Sorter Server is running!");
 
            while (true) {
                Socket socket = serverSocket.accept();
                new Sorter(socket, clientNumber++).run();
            }
 
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
	}

	private static class Sorter extends Thread {
		private Socket socket;
		private int clientNumber;

		public Sorter(Socket socket, int clientNumber) {
			this.socket = socket;
			this.clientNumber = clientNumber;
			System.out.println("New client #" + clientNumber + 
					" connected at " + socket);
			}

		public void run() {
			try {
                InputStream in = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
 
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);
                
                writer.println("Hello, you are client #" + clientNumber);
 
                while (true) {
                	String input = reader.readLine();
                	if (input == null || input.isEmpty()) {
                		break;
                	}
                	//Put it in a string array
                	String[] nums = input.split(" ");
                	//Convert this string array to an int array
                	int[] intarr = new int[ nums.length ];
                	int i = 0;
                	int isNumber = 1;
                	for ( String textValue : nums ) {
                		try {
                			intarr[i] = Integer.parseInt( textValue );                			
                			i++;
                		} catch (Exception e) {
                			writer.println("Input sequence has a non-number element. Please enter again.");
                			isNumber = 0;
                			break;
                		}
                	}
                	if (isNumber == 0) continue;
                	//Sort the numbers in this int array
                	new SelectionSort().sort(intarr);
                	//Convert the int array to String
                	String strArray[] = Arrays.stream(intarr)
                						.mapToObj(String::valueOf)
                						.toArray(String[]::new);
                	//Send the result to Client
                	writer.println("Output: " + Arrays.toString(strArray));
                	}
				
				} catch (IOException e) {
					System.out.println("Error handling client #" + clientNumber);
				} finally {
					try { 
						socket.close(); 
					} catch (IOException e) {
						
					}
					System.out.println("Connection with client # " +
										clientNumber + " closed");
				}
			}
	}
}

