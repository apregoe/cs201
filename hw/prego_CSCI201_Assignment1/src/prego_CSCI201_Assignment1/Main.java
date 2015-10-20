package prego_CSCI201_Assignment1;

import java.nio.file.Files;
import java.util.Scanner;
import java.io.File;
import java.util.LinkedList;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.InputMismatchException;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Map;
import java.lang.SecurityException;
import java.io.FileInputStream;
import java.util.HashMap;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.io.BufferedReader;
import java.lang.Integer;
import java.util.ListIterator;
import java.io.FileReader;
import java.lang.NullPointerException;

class MyComparedDirectories{
	MyComparedDirectories(MyDirectory d1, MyDirectory d2) throws FileNotFoundException{
		if(!d1.exists() || !d1.isDirectory()){
			throw new FileNotFoundException("FileNotFoundException on dir1 MyComparedDirectories constructor");
		}
		if(!d2.exists() || !d2.isDirectory()){
			throw new FileNotFoundException("FileNotFoundException on dir2 MyComparedDirectories constructor");
		}
		dir1 = d1;
		dir2 = d2;
		filesDir1_ = dir1.allFiles();
		filesDir2_ = dir2.allFiles();
		sharedFiles_ = new LinkedList<String>();
		for(Map.Entry<String, MyFile> it1 : filesDir1_.entrySet()){
			for(Map.Entry<String, MyFile> it2 : filesDir2_.entrySet()){
				if(it1.getKey().equals(it2.getKey())){
					sharedFiles_.add(it1.getKey());
				}
			}
		}
	}
	public void print(){
		System.out.println("Directory: "+dir1.getName()+"     "+dir2.getName());
		System.out.println("Number of Files: " + dir1.numberOfFiles()+"     "+dir2.numberOfFiles());
		System.out.println("Largest File: " + dir1.largestFile()+"("+dir1.largestFileSize()+" byte(s))"+"     "+dir2.largestFile()+"("+dir2.largestFileSize()+" byte(s))");
		System.out.println("Smallest File: " + dir1.smallestFile()+"("+dir1.smallestFileSize()+" byte(s))"+"     "+dir2.smallestFile()+"("+dir2.smallestFileSize()+" byte(s))");
		System.out.println("Averge File Size: "+ dir1.averageFileSize()+ " byte(s)"+dir2.averageFileSize()+" byte(s)");
		System.out.println("Files with same name:");
		ListIterator it = sharedFiles_.listIterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
	}
	public void writeReport(String filePath, boolean append){
		FileWriter fw = null;
		PrintWriter pw = null;
		try{
			if(append){fw = new FileWriter(filePath, true);
			}else{ fw = new FileWriter(filePath, false);}
			pw = new PrintWriter(fw);
			pw.println("Directory: "+dir1.getName()+"     "+dir2.getName());
			pw.println("Number of Files: " + dir1.numberOfFiles()+"     "+dir2.numberOfFiles());
			pw.println("Largest File: " + dir1.largestFile()+"("+dir1.largestFileSize()+" byte(s))"+"     "+dir2.largestFile()+"("+dir2.largestFileSize()+" byte(s))");
			pw.println("Smallest File: " + dir1.smallestFile()+"("+dir1.smallestFileSize()+" byte(s))"+"     "+dir2.smallestFile()+"("+dir2.smallestFileSize()+" byte(s))");
			pw.println("Averge File Size: "+ dir1.averageFileSize()+ " byte(s)"+dir2.averageFileSize()+" byte(s)");
			pw.println("Files with same name:");
			ListIterator it = sharedFiles_.listIterator();
			while(it.hasNext()){
				pw.println(it.next());
			}
			pw.flush();
		}
		catch(IOException ioe){
			System.out.println("IOException on MyComparedDirectories.writeReport()\n" + ioe.getMessage());
		} finally{
			try{
				if(pw != null){pw.close();}
				if(fw != null){fw.close();}
			} catch(IOException ioe){
				System.out.println("IOException on closing fw and pw in MyComparedDirectories.writeReport()\n" + ioe.getMessage());
			}
		}
	}
	MyDirectory dir1;
	MyDirectory dir2;
	HashMap<String, MyFile> filesDir1_;
	HashMap<String, MyFile> filesDir2_;
	LinkedList<String> sharedFiles_;
}

class MyDirectory extends File{
	MyDirectory(String path)throws FileNotFoundException{
		super(path);
		smallestFileSize_ = Long.MAX_VALUE;
		largestFileSize_ = 0;
		smallestFile_ = "";
		largestFile_ = "";
		totalFilesSize_ = 0;
		allFiles_ = new HashMap<String, MyFile>();
		if(!super.isDirectory()) {
			throw new FileNotFoundException("Directory does not exists");
		}else{
			File[] files = super.listFiles();
			for (int i = 0;i < files.length; ++i){
				if(files[i].exists() && !files[i].isDirectory() && files[i].isFile()){
					try{
						allFiles_.put(files[i].getName(), new MyFile(files[i].toString()));
					} catch(FileNotFoundException fnfe){
						System.out.println("On MyDirectory constructor FileNotFoundException\n "+fnfe.getMessage());
					}
					if(smallestFileSize_ > files[i].length()){
						smallestFileSize_ = files[i].length();
						smallestFile_ = files[i].getName();
					}
					if(largestFileSize_ < files[i].length()){
						largestFileSize_ = files[i].length();
						largestFile_ = files[i].getName();
					}
					totalFilesSize_ += files[i].length();
				}
			}
		}
	}

	public void writeFilesReport(String fileName){
		String absPathString = new String(super.getAbsolutePath()+"/"+fileName);
		allFiles_.remove(fileName);
		File file = new File(absPathString);
		try{
			Files.deleteIfExists(Paths.get(absPathString));
		} catch(IOException ioe){
			System.out.println("IOException on MyDirectory.writeReport()\n" + ioe.getMessage());
		} catch(SecurityException se){
			System.out.println("SecurityException on MyDirectory.writeReport()\n" + se.getMessage());
		}

		for(Map.Entry<String, MyFile> it : allFiles_.entrySet()){
			it.getValue().writeReport(absPathString, true);
		}
	}
	public void writeDirectoryReport(String filePath, boolean append){
		FileWriter fw = null;
		PrintWriter pw = null;
		try{
			if(append){fw = new FileWriter(filePath, true);
			}else{ fw = new FileWriter(filePath, false);}
			pw = new PrintWriter(fw);
			pw.println("Directory: " + super.getName());
			pw.println("Number of Files: " + numberOfFiles());
			pw.println("Largest File: " + largestFile()+"("+largestFileSize()+" byte(s)");
			pw.println("Smallest File: " + smallestFile()+"("+smallestFileSize()+" byte(s)");
			pw.println("Averge File Size: "+ averageFileSize()+ "byte(s)");
			pw.flush();
		}
		catch(IOException ioe){
			System.out.println("IOException on MyDirectory.writeDirectoryReport()\n" + ioe.getMessage());
		} finally{
			try{
				if(pw != null){pw.close();}
				if(fw != null){fw.close();}
			} catch(IOException ioe){
				System.out.println("IOException on closing fw and pw in MyDirectory.writeDirectoryReport()\n" + ioe.getMessage());
			}
		}
	}
	public void print(){
		System.out.println("Number of Files: " + numberOfFiles());
		System.out.println("Largest File: " + largestFile()+"("+largestFileSize()+" byte(s)");
		System.out.println("Smallest File: " + smallestFile()+"("+smallestFileSize()+" byte(s)");
		System.out.println("Averge File Size: "+ averageFileSize()+ "byte(s)");
	}

	public int numberOfFiles(){return allFiles_.size();}
	public String largestFile(){return largestFile_;}
	public long largestFileSize(){return largestFileSize_;}
	public String smallestFile(){ return smallestFile_;}
	public long smallestFileSize(){ return smallestFileSize_;}
	public long averageFileSize(){return totalFilesSize_/((long)numberOfFiles());}
	public HashMap<String, MyFile> allFiles(){return allFiles_;}
	private HashMap<String, MyFile> allFiles_;
	String smallestFile_;
	long smallestFileSize_;
	String largestFile_;
	long largestFileSize_;
	long totalFilesSize_;
}

class MyComparedFiles{
	MyComparedFiles(MyFile f1, MyFile f2) throws FileNotFoundException{
		if(!f1.exists() || f1.isDirectory() || !f1.isFile()){
			throw new FileNotFoundException("FileNotFoundException on file1 MyComparedFiles constructor");
		}
		if(!f2.exists() || f2.isDirectory() || !f2.isFile()){
			throw new FileNotFoundException("FileNotFoundException on file2 MyComparedFiles constructor");
		}
		file1 = f1;
		file2 = f2;
		FileInputStream fis1 = null;
		BufferedReader br1 = null;
		FileInputStream fis2 = null;
		BufferedReader br2 = null;
		sharedLines_ = new LinkedList<String>();
		wordsFile1_ = new HashMap<String, Integer>();
		wordsFile2_ = new HashMap<String, Integer>();
		try{
			fis1 = new FileInputStream(f1.getAbsolutePath());
			br1 = new BufferedReader(new InputStreamReader(fis1));
			fis2 = new FileInputStream(f2.getAbsolutePath());
			br2 = new BufferedReader(new InputStreamReader(fis2));
			String line1 = "";
			String line2 = "";
			while((line1 = br1.readLine()) != null){
				//getting words of line1
				String[] words1 = line1.split(" ");
				for(int i = 0; i < words1.length; ++i){
					words1[i] = words1[i].toLowerCase();
					for(int j = 0; j < words1[i].length(); ++j){
						int c = (int)(words1[i].charAt(j));
						if(!(((c >= 97) && (c <= 122)) || ((c >= 48) && (c <= 57)) || (c == 39) || (c == 45))){
							words1[i] = words1[i].replace((char)c, (char)(0));
						}
					}
					if(!wordsFile1_.containsKey(words1[i])){
						wordsFile1_.put(words1[i], 1);
					}else{
						wordsFile1_.put(words1[i], wordsFile1_.get(words1[i])+1);
					}
				}
				//comparing words and lines
				while((line2 = br2.readLine()) != null){
					//comparing lines
					if(line1.equals(line2)){
						sharedLines_.add(line1);
					}
				}
				fis2.getChannel().position(0);
				br2 = new BufferedReader(new InputStreamReader(fis2));
			}
			//adding words of file 2
			while((line2 = br2.readLine()) != null){
				//getting words of line2
				String[] words2 = line2.split(" ");
				for(int i = 0; i < words2.length; ++i){
					words2[i] = words2[i].toLowerCase();
					for(int j = 0; j < words2[i].length(); ++j){
						int c = (int)(words2[i].charAt(j));
						if(!(((c >= 97) && (c <= 122)) || ((c >= 48) && (c <= 57)) || (c == 39) || (c == 45))){
							words2[i] = words2[i].replace((char)c, (char)(0));//changing unanted char to nothing
						}
					}
					if(!wordsFile2_.containsKey(words2[i])){
						wordsFile2_.put(words2[i], 1);
					}else{
						wordsFile2_.put(words2[i], wordsFile2_.get(words2[i]).intValue()+1);
					}
				}


			}
			//getting most common word
			int maxRepetition = 0;
			for(Map.Entry<String, Integer> it : wordsFile1_.entrySet()){
				int currentMinRepetition = 0;
				if(wordsFile2_.containsKey(it.getKey())){
					if(wordsFile2_.get(it.getKey()).intValue() < it.getValue().intValue()){ 
						currentMinRepetition = wordsFile2_.get(it.getKey()).intValue();
					}else{
						currentMinRepetition = it.getValue().intValue();
					}
					if(maxRepetition < currentMinRepetition){
						mostFrequentWord_ = it.getKey();
						maxRepetition = currentMinRepetition;
					}
				}
			}
			//reseting both streams to pos 0 to get mos common words
			fis1.getChannel().position(0);
			br1 = new BufferedReader(new InputStreamReader(fis1));
			fis2.getChannel().position(0);
			br2 = new BufferedReader(new InputStreamReader(fis2));

		} catch(IOException ioe){
			System.out.println("IOException in MyComparedFiles constructor\n" + ioe.getMessage());
		}
	}
	public String mostFrequentWord(){return mostFrequentWord_;}
	public LinkedList<String> commonLines(){return sharedLines_;}
	public void writeReport(String filePath, boolean append){
		FileWriter fw = null;
		PrintWriter pw = null;
		try{
			if(append){fw = new FileWriter(filePath, true);
			}else{ fw = new FileWriter(filePath, false);}
			pw = new PrintWriter(fw);
			pw.println("File: "+file1.getName()+"     "+file2.getName());
			pw.println("File Size: "+ (int)file1.fileSize()+"     "+(int)file2.fileSize());
			pw.println("Most frequent char: "+file1.mostFrequentchar()+"     "+file2.mostFrequentchar());
			pw.println("Number of characters: "+(int)file1.charCount()+"     "+(int)file2.charCount());
			pw.println("Word count: " + (int)file1.wordCount()+"     "+(int)file2.wordCount());
			pw.println("Most frequent word: " + file1.mostFrequentWord()+"     "+file2.mostFrequentWord());
			pw.println("Average Word length: " + file1.averageWordLength()+"     "+file2.averageWordLength());
			pw.println("Longest word: " + file1.longestWord()+"     "+file2.longestWord());
			pw.println("Shortest word: " + file1.shortestWord()+"     "+file2.shortestWord());
			pw.println("Number of sentences: " + (int)file1.sentenceCount()+"     "+(int)file2.sentenceCount());
			pw.println("Average Sentenge Length (words): " + file1.averageSentenceLength()+"     "+file2.averageSentenceLength());
			pw.println("Longest Sentence: " + file1.longestSentence()+"     "+file2.longestSentence());
			pw.println("Shortest Sentence: " + file1.shortestSentence()+"     "+file2.shortestSentence());
			pw.println("Lines in common:");
			if(sharedLines_.size() > 0){
				ListIterator<String> it = sharedLines_.listIterator();
				while(it.hasNext()){
					pw.println(it.next());
				}
			}else{
				pw.println("None.");
			}
			pw.println("Most common word between files: " + mostFrequentWord_);
			pw.flush();
		}
		catch(IOException ioe){
			System.out.println("IOException in MyComparedFiles.writeReport()\n" + ioe.getMessage());
		} finally{
			try{
				if(pw != null){pw.close();}
				if(fw != null){fw.close();}
			} catch(IOException ioe){
				System.out.println("IOException on closing fw and pw in MyComparedFiles.writeReport()\n" + ioe.getMessage());
			}
		}
	}
	public void print(){
		System.out.println("File: "+file1.getName()+"     "+file2.getName());
		System.out.println("File Size: "+ (int)file1.fileSize()+"     "+(int)file2.fileSize());
		System.out.println("Most frequent char: "+file1.mostFrequentchar()+"     "+file2.mostFrequentchar());
		System.out.println("Number of characters: "+(int)file1.charCount()+"     "+(int)file2.charCount());
		System.out.println("Word count: " + (int)file1.wordCount()+"     "+(int)file2.wordCount());
		System.out.println("Most frequent word: " + file1.mostFrequentWord()+"     "+file2.mostFrequentWord());
		System.out.println("Average Word length: " + file1.averageWordLength()+"     "+file2.averageWordLength());
		System.out.println("Longest word: " + file1.longestWord()+"     "+file2.longestWord());
		System.out.println("Shortest word: " + file1.shortestWord()+"     "+file2.shortestWord());
		System.out.println("Number of sentences: " + (int)file1.sentenceCount()+"     "+(int)file2.sentenceCount());
		System.out.println("Average Sentenge Length (words): " + file1.averageSentenceLength()+"     "+file2.averageSentenceLength());
		System.out.println("Longest Sentence: " + file1.longestSentence()+"     "+file2.longestSentence());
		System.out.println("Shortest Sentence: " + file1.shortestSentence()+"     "+file2.shortestSentence());
		System.out.println("Lines in common:");
		if(sharedLines_.size() > 0){
			ListIterator<String> it = sharedLines_.listIterator();
			while(it.hasNext()){
				System.out.println(it.next());
			}
		}else{
			System.out.println("None.");
		}
		System.out.println("Most common word between files: " + mostFrequentWord_);
	}
	private LinkedList<String> sharedLines_;
	private HashMap<String, Integer> wordsFile1_;
	private HashMap<String, Integer> wordsFile2_;
	private String mostFrequentWord_;
	private MyFile file1;
	private MyFile file2;
}

class MyFile extends File{

	MyFile(String path) throws FileNotFoundException{
		super(path);
		path_ = path;
		//initializing variables
		charCount_ = 0;
		wordCount_ = 0;
		totalWordLengthCount_ = 0;
		mostFrequentCharCount_ = 0;
		mostFrequentWordCount_ = 0;
		sentenceCount_ = 0;
		totalWordPerSentenceCount_ = 0;
		maxSentenceLength_ = 0;
		longestSentence_ = "";
		String sentenceWord = "";
		int currentSentenceLength = 0;
		String currentSentence = "";
		Boolean hasCapital = false;
		shortestWordLength_ = Integer.MAX_VALUE;
		shortestSentenceLength_ = Integer.MAX_VALUE;
		shortestSentence_ = "";
		shortestWord_ = "";
		longestWord_ = "";
		allChars_ = new HashMap<Character, Integer>();
		allWords_ = new HashMap<String, Integer>();
		if(super.exists() && !super.isDirectory() && super.isFile()){
			FileReader fileReader;
			BufferedReader bufferedReader;
			try{
				fileReader = new FileReader(path);
				bufferedReader = new BufferedReader(fileReader);
				int r;
				String currentWord = new String();
				char prevChar = '\0';
				//reading char by char
				while((r = bufferedReader.read()) != -1){
					//converting it to lowercase, this does not make it case sensitive
					if(((r >= 65) && (r <= 90))){
						hasCapital = true;
						r += 32;
					}
					char currentChar = (char)r;
					//if its a comma exclamation or, add it to the current sentence
					if(r == 44){
						currentSentence += ",";
					}
					//is it a character (0-9, A-Z, a-z)?
					if(((r >= 48) && (r <= 57)) || ((r >= 97) && (r <= 122)) /*|| ((r >= 65) && (r <= 90))*/){
						++charCount_;
						if(!allChars_.containsKey(currentChar)){
							allChars_.put(currentChar, 1);
							if(mostFrequentCharCount_ < 1){
								mostFrequentChar_ = currentChar;
								mostFrequentCharCount_ = 1;
							}
						} else{
							if(mostFrequentCharCount_ < (allChars_.get(currentChar) + 1)){
								mostFrequentChar_ = currentChar;
								mostFrequentCharCount_ = allChars_.get(currentChar) + 1;
							}
							allChars_.put(currentChar, allChars_.get(currentChar) + 1);
						}
						currentWord = currentWord + currentChar;
					} else if((r == 39) || (r == 45)){ //or is it a ' or - ?
						currentWord = currentWord + currentChar;
					} else{//if it is not a-z or 0-9 it means it is end of word
						sentenceWord = currentWord;
						if(hasCapital.equals(new Boolean(true))){
							String replacement = "" + (char)((int)sentenceWord.charAt(0) - 32);
							String replaced = "" + sentenceWord.charAt(0);
							sentenceWord = sentenceWord.replaceFirst(replaced, replacement);
							hasCapital = false;
						}
						if(r == 32){
							int prevInt = (int)prevChar;
							//, . ! ?
							if(!((prevInt == 44) || (prevInt == 46) || (prevInt == 33) || (prevInt == 63))){//end of word
								++wordCount_;
								++totalWordPerSentenceCount_;
								if(!allWords_.containsKey(currentWord)){
									allWords_.put(currentWord,1);
									if(mostFrequentWordCount_ < 1){
										mostFrequentWord_ = currentWord;
										++mostFrequentWordCount_;
									}
								} else{
									if(mostFrequentWordCount_ < (allWords_.get(currentWord) + 1)){
										mostFrequentWord_ = currentWord;
										++mostFrequentWordCount_;
									}
									allWords_.put(currentWord, allWords_.get(currentWord) + 1);
								}
								if(longestWord_.length() < currentWord.length()){
									longestWord_ = currentWord;
								}
								if(shortestWordLength_ > currentWord.length()){
									shortestWord_ = currentWord;
									shortestWordLength_ = currentWord.length();
								}
								totalWordLengthCount_ = totalWordLengthCount_ + (double)currentWord.length();
								++currentSentenceLength;
								currentSentence = currentSentence +  " " + sentenceWord;
								sentenceWord = "";
								currentWord = "";
							}
						} else if((r == 44) || (r == 46) || (r == 33) || (r == 63)){//end of word
							++wordCount_;
							++totalWordPerSentenceCount_;
							if(!allWords_.containsKey(currentWord)){
								allWords_.put(currentWord,1);
								if(mostFrequentWordCount_ < 1){
									mostFrequentWord_ = currentWord;
									++mostFrequentWordCount_;
								}
							} else{
								if(mostFrequentWordCount_ < (allWords_.get(currentWord) + 1)){
									mostFrequentWord_ = currentWord;
									++mostFrequentWordCount_;
								}
								allWords_.put(currentWord, allWords_.get(currentWord) + 1);
							}
							if(longestWord_.length() < currentWord.length()){
								longestWord_ = currentWord;
							}
							if(shortestWordLength_ > currentWord.length()){
								shortestWord_ = currentWord;
								shortestWordLength_ = currentWord.length();
							}
							totalWordLengthCount_ = totalWordLengthCount_ + (double)currentWord.length();
							++currentSentenceLength;
							currentSentence = currentSentence +  " " + sentenceWord;
							sentenceWord = "";
							currentWord = "";

							if((r == 46) || (r ==33) || (r ==63)){//. ! ? End of a sentence
								sentenceCount_ = sentenceCount_ + 1;
								if(maxSentenceLength_ < currentSentenceLength){
									maxSentenceLength_ = currentSentenceLength;
									longestSentence_ = currentSentence;
								}
								currentSentenceLength = 0;
								if(shortestSentenceLength_ > sentenceCount_){
									shortestSentenceLength_ = currentSentenceLength;
									shortestSentence_ = currentSentence;
								}
								currentSentence = "";
							}
						}
					}
					prevChar = currentChar;
				}
				//in case the text does not ends with a ". ! or ?" end of sentence word and text
				if((((int)prevChar >= 48) && ((int)prevChar <= 57)) || (((int)prevChar >= 97) && ((int)prevChar <= 122))){
					if(!allWords_.containsKey(currentWord)){
						allWords_.put(currentWord,1);
						if(mostFrequentWordCount_ < 1){
							mostFrequentWord_ = currentWord;
							++mostFrequentWordCount_;
						}
					} else{
						if(mostFrequentWordCount_ < (allWords_.get(currentWord) + 1)){
							mostFrequentWord_ = currentWord;
							++mostFrequentWordCount_;
						}
						allWords_.put(currentWord, allWords_.get(currentWord) + 1);
					}
					if(longestWord_.length() < currentWord.length()){
						longestWord_ = currentWord;
					}
					if(shortestWordLength_ > currentWord.length()){
						shortestWord_ = currentWord;
						shortestWordLength_ = currentWord.length();
					}
					totalWordLengthCount_ = totalWordLengthCount_ + (double)currentWord.length();
					++wordCount_;
					++totalWordPerSentenceCount_;
					++currentSentenceLength;
					currentSentence = currentSentence +  " " + sentenceWord;
					sentenceWord = "";
					sentenceCount_ = sentenceCount_ + 1;
					if(maxSentenceLength_ < currentSentenceLength){
						maxSentenceLength_ = currentSentenceLength;
						longestSentence_ = currentSentence;
					}
					currentSentenceLength = 0;
					currentSentence = "";
				} else if(((int)prevChar == 39) || ((int)prevChar == 45)){// ' or -
					if(!allWords_.containsKey(currentWord)){
						allWords_.put(currentWord,1);
						if(mostFrequentWordCount_ < 1){
							mostFrequentWord_ = currentWord;
							++mostFrequentWordCount_;
						}
					} else{
						if(mostFrequentWordCount_ < (allWords_.get(currentWord) + 1)){
							mostFrequentWord_ = currentWord;
							++mostFrequentWordCount_;
						}
						allWords_.put(currentWord, allWords_.get(currentWord) + 1);
					}
					if(longestWord_.length() < currentWord.length()){
						longestWord_ = currentWord;
					}
					if(shortestWordLength_ > currentWord.length()){
						shortestWord_ = currentWord;
						shortestWordLength_ = currentWord.length();
					}
					totalWordLengthCount_ = totalWordLengthCount_ + (double)currentWord.length();
					++wordCount_;
					++totalWordPerSentenceCount_;
					++currentSentenceLength;
					currentSentence = currentSentence +  " " + sentenceWord;
					sentenceWord = "";
					sentenceCount_ = sentenceCount_ + 1;
					if(maxSentenceLength_ < currentSentenceLength){
						maxSentenceLength_ = currentSentenceLength;
						longestSentence_ = currentSentence;
					}
					currentSentenceLength = 0;
					currentSentence = "";
				}
			} catch(IOException ioe){
				//do something
				System.out.println(ioe.getMessage());
			}
		} else{
			throw new FileNotFoundException("The file is not a text file or does not exist");
		}
	}

	private String path_;

	private long sizeLong;

	private double charCount_;
	private char mostFrequentChar_;
	private int mostFrequentCharCount_;
	private HashMap<Character, Integer> allChars_;

	private String mostFrequentWord_;
	private int mostFrequentWordCount_;
	private HashMap<String, Integer> allWords_;
	private double wordCount_;
	private double totalWordLengthCount_;
	private String longestWord_;
	private String shortestWord_;
	private int shortestWordLength_;

	private double sentenceCount_;
	private double totalWordPerSentenceCount_;
	private int maxSentenceLength_;
	private int shortestSentenceLength_;
	private String longestSentence_;
	private String shortestSentence_;

	public void writeReport(String filePath, boolean append){
		FileWriter fw = null;
		PrintWriter pw = null;
		try{
			if(append){fw = new FileWriter(filePath, true);
			}else{ fw = new FileWriter(filePath, false);}
			pw = new PrintWriter(fw);
			pw.println("File: " + super.getName());
			pw.println("File size: " + fileSize());
			pw.println("Total chars: " + (int)charCount());
			pw.println("Most frequent char: " + mostFrequentchar());
			pw.println("Word count: " + (int)wordCount());
			pw.println("Most frequent word: " + mostFrequentWord());
			pw.println("Average Word length: " + averageWordLength());
			pw.println("Longest word: " + longestWord());
			pw.println("Shortest word: " + shortestWord());
			pw.println("Number of sentences: " + (int)sentenceCount());
			pw.println("Average Sentenge Length (words): " + averageSentenceLength());
			pw.println("Longest Sentence: " + longestSentence());
			pw.println("Shortest Sentence: " + shortestSentence());
			pw.flush();
		}
		catch(IOException ioe){
			System.out.println("IOException on MyFile.writeReport()\n" + ioe.getMessage());
		} finally{
			try{
				if(pw != null){pw.close();}
				if(fw != null){fw.close();}
			} catch(IOException ioe){
				System.out.println("IOException on closing fw and pw in MyFile.writeReport()\n" + ioe.getMessage());
			}
		}
	}
	public void print(){
		System.out.println("File size: " + fileSize());
		System.out.println("Total chars: " + (int)charCount());
		System.out.println("Most frequent char: " + mostFrequentchar());
		System.out.println("Word count: " + (int)wordCount());
		System.out.println("Most frequent word: " + mostFrequentWord());
		System.out.println("Average Word length: " + averageWordLength());
		System.out.println("Longest word: " + longestWord());
		System.out.println("Shortest word: " + shortestWord());
		System.out.println("Number of sentences: " + (int)sentenceCount());
		System.out.println("Average Sentenge Length (words): " + averageSentenceLength());
		System.out.println("Longest Sentence: " + longestSentence());
		System.out.println("Shortest Sentence: " + shortestSentence());
	}

	public long fileSize(){
		try{
			sizeLong = Files.size(Paths.get(path_));
		} catch(IOException ioe){
			System.out.println("Couldn't get fileSize(). \n" + ioe.getMessage());
			sizeLong = 0;
		}
		return sizeLong;
	}
	public double charCount(){return charCount_;}
	public char mostFrequentchar(){return mostFrequentChar_;}
	public double wordCount(){return wordCount_;}
	public String mostFrequentWord(){return mostFrequentWord_;}
	public int averageWordLength(){return (int)(totalWordLengthCount_/wordCount());}
	public String longestWord(){return longestWord_;}
	public String shortestWord(){return shortestWord_;}
	public double sentenceCount(){return sentenceCount_;}
	public String longestSentence(){return longestSentence_;}
	public String shortestSentence(){return shortestSentence_;}
	public int averageSentenceLength(){return (int)(totalWordPerSentenceCount_/sentenceCount_);}
}

public class Main{
	public static void main(String [] args){
		Scanner scan = new Scanner(System.in);
		int selection = 0;
		while(true){
			System.out.println("1. File Report");
			System.out.println("2. Directory Report");
			System.out.println("3. Compare two files");
			System.out.println("4. Compare two directores");
			System.out.println("5. Exit");
			while(true){
				try{
					selection = scan.nextInt();
					break;
				} catch (InputMismatchException ime){
					System.out.println("Please write an integer");
					scan.next();
				}
			}
			if(selection == 1){
				System.out.println("Please enter the file path: ");
				String path = scan.next();
				if(path.equals("exit")){
					continue;
				}
				MyFile file;
				while(true){
					try{
						file = new MyFile(path);
						file.print();
						String save ="";
						while(true){
							System.out.println("Do you wish to save the report? Answer with y = yes, n = no");
							save = scan.next();
							if(save.equals("y") || save.equals("Y")){
								java.util.Date date = new java.util.Date();
								String timeStamp = (new Timestamp(date.getTime())).toString();
								file.writeReport(timeStamp,false);
								System.out.println("Report saved as: " + timeStamp);
								System.out.println("Report was saved in the current workspace.");
								break;
							}else if(save.equals("n") || save.equals("N")){
								System.out.println("Goodbye!");
								break;
							}else{
								System.out.println("Sorry, didn't understand that.\n Please answer with y or n");
								continue;
							}
						}
						if(save.equals("n") || save.equals("N")){
							break;
						}
					} catch(FileNotFoundException fnfe){
						System.out.println(fnfe.getMessage() + ", please type another path:");
						path = scan.next();
						if(path.equals("exit")){
							break;
						}
						continue;
					} catch(NullPointerException npe){
						System.out.println("NullPointerException\n" + npe.getMessage());
						path = scan.next();
						if(path.equals("exit")){
							break;
						}
						continue;
					}
					System.out.println("Please enter a file path: ");
					path = scan.next();
					if(path.equals("exit")){
						break;
					}
				}
			}else if(selection == 2){
				System.out.print("Please enter a directory path: ");
				String pathString = scan.next();
				if(pathString.equals("exit")){
					continue;
				}
				MyDirectory dir;
				while(true){
					try{
						dir = new MyDirectory(pathString);
						dir.print();
						dir.writeFilesReport(dir.getName() + "-files.txt");
						String save ="";
						while(true){
							System.out.println("Do you wish to save the report? Answer with y = yes, n = no");
							save = scan.next();
							if(save.equals("y") || save.equals("Y")){
								java.util.Date date = new java.util.Date();
								String timeStamp = (new Timestamp(date.getTime())).toString();
								dir.writeDirectoryReport(timeStamp,false);
								System.out.println("Report saved as: " + timeStamp);
								System.out.println("Report was saved in the current workspace.");
								break;
							}else if(save.equals("n") || save.equals("N")){
								System.out.println("Goodbye!");
								break;
							}else{
								System.out.println("Sorry, didn't understand that.\n Please answer with y or n");
								continue;
							}
						}
						if(save.equals("n") || save.equals("N")){
							break;
						}
					}catch(FileNotFoundException fnfe){
						System.out.println(fnfe.getMessage());
						System.out.print("Please enter a directory path: ");
						pathString = scan.next();
						if(pathString.equals("exit")){
							break;
						}
						continue;
					}
					System.out.println("Please enter a directory path: ");
					pathString = scan.next();
					if(pathString.equals("exit")){
						break;
					}
				}
			} else if(selection == 3){
				MyFile file1 = null;
				MyFile file2 = null;
				String pathString = "";
				while(true){
					while(true){
						try{
							System.out.println("Enter first file path: ");
							pathString = scan.next();
							file1 = new MyFile(pathString);
							break;
						} catch(FileNotFoundException fnfe){
							System.out.println(fnfe.getMessage());
						} finally{
							if(pathString.equals("exit")){break;}
						}
					}
					if(pathString.equals("exit")){break;}
					while(true){
						try{
							System.out.println("Enter second file path: ");
							pathString = scan.next();
							file2 = new MyFile(pathString);
							break;
						} catch(FileNotFoundException fnfe){
							System.out.println(fnfe.getMessage());
						} finally{
							if(pathString.equals("exit")){break;}
						}
					}
					if(pathString.equals("exit")){break;}
					if((file1 != null) && (file2 != null)){
						try{
							MyComparedFiles comparedFiles = new MyComparedFiles(file1, file2);
							comparedFiles.print();
							String save ="";
							while(true){
								System.out.println("Do you wish to save the report? Answer with y = yes, n = no");
								save = scan.next();
								if(save.equals("y") || save.equals("Y")){
									java.util.Date date = new java.util.Date();
									String timeStamp = (new Timestamp(date.getTime())).toString();
									comparedFiles.writeReport(timeStamp,false);
									System.out.println("Report saved as: " + timeStamp);
									System.out.println("Report was saved in the current workspace.");
									break;
								}else if(save.equals("n") || save.equals("N")){
									System.out.println("Goodbye!");
									break;
								}else{
									System.out.println("Sorry, didn't understand that.\n Please answer with y or n");
									continue;
								}
							}
							if(save.equals("n") || save.equals("N")){
								break;
							}
						} catch(FileNotFoundException fnfe){
							System.out.println(fnfe.getMessage());
						}
					}
				}
			} else if(selection == 4){
				MyDirectory dir1 = null;
				MyDirectory dir2 = null;
				String pathString = "";
				while(true){
					while(true){
						try{
							System.out.println("Enter first directory path: ");
							pathString = scan.next();
							dir1 = new MyDirectory(pathString);
							break;
						} catch(FileNotFoundException fnfe){
							System.out.println(fnfe.getMessage());
						} finally{
							if(pathString.equals("exit")){break;}
						}
					}
					if(pathString.equals("exit")){break;}
					while(true){
						try{
							System.out.println("Enter second directory path: ");
							pathString = scan.next();
							dir2 = new MyDirectory(pathString);
							break;
						} catch(FileNotFoundException fnfe){
							System.out.println(fnfe.getMessage());
						} finally{
							if(pathString.equals("exit")){break;}
						}
					}
					if(pathString.equals("exit")){break;}
					if((dir1 != null) && (dir2 != null)){
						try{
							MyComparedDirectories comparedDirectories = new MyComparedDirectories(dir1, dir2);
							comparedDirectories.print();
							String save ="";
							while(true){
								System.out.println("Do you wish to save the report? Answer with y = yes, n = no");
								save = scan.next();
								if(save.equals("y") || save.equals("Y")){
									java.util.Date date = new java.util.Date();
									String timeStamp = (new Timestamp(date.getTime())).toString();
									comparedDirectories.writeReport(timeStamp,false);
									System.out.println("Report saved as: " + timeStamp);
									System.out.println("Report was saved in the current workspace.");
									break;
								}else if(save.equals("n") || save.equals("N")){
									System.out.println("Goodbye!");
									break;
								}else{
									System.out.println("Sorry, didn't understand that.\n Please answer with y or n");
									continue;
								}
							}
							if(save.equals("n") || save.equals("N")){
								break;
							}
						} catch(FileNotFoundException fnfe){
							System.out.println(fnfe.getMessage());
						}
					}
				}
			}else if (selection == 5){
				break;
			}else{
				System.out.println("Please enter a value between 1 and 5");
			}
		}
		scan.close();
	}
}

















