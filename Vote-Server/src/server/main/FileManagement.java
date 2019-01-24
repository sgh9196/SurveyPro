package server.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileManagement {

	public boolean serverFileWrite(String ... _data) throws IOException {
		
		boolean tmp = false;
		File file = null;
		FileWriter fw = null;
		
		try {
			
			file = new File("../data.txt");
			fw = new FileWriter(file);
			
			fw.write(_data[0] + "\n"); fw.write(_data[1] + "\n"); 
			fw.write(_data[2]); fw.flush();
			fw.close();
			
			tmp = true;
			
		} catch(IOException e) {
			System.out.println("FileManagement Write Err -> " + e.getMessage());
		}
		
		return tmp;
		
	}
	
	public void serverFileDelete() {
		
		File file = new File("../data.txt");
		
		if(file.exists()) {
			file.delete();
			System.out.println("파일 삭제 성공");
		}
		
	}
	
}
