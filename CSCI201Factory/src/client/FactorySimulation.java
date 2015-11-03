package client;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import resource.Factory;
import resource.Resource;

public class FactorySimulation {
	
	Factory mFactory;
	private double totalTime;
	private ArrayList<FactoryObject> mFObjects;
	private ArrayList<FactoryWorker> mFWorkers;
	private FactoryNode mFNodes[][];
	private Map<String, FactoryNode> mFNodeMap;
	private FactoryTaskBoard mTaskBoard;
	private FactoryMailbox mMailbox;
	private boolean isDone = false;

	//instance constructor
	{
		mFObjects = new ArrayList<FactoryObject>();
		mFWorkers = new ArrayList<FactoryWorker>();
		mFNodeMap = new HashMap<String, FactoryNode>();
	}
	
	FactorySimulation(Factory inFactory, JTable inTable) {
		totalTime = 0.0;
		mFactory = inFactory;
		mFNodes = new FactoryNode[mFactory.getWidth()][mFactory.getHeight()];
		System.out.println(inFactory.getWidth() +"   " +inFactory.getHeight());

		//Create the nodes of the factory
		for(int height = 0; height < mFactory.getHeight(); height++) {
			for(int width = 0; width < mFactory.getWidth(); width++) {
				mFNodes[width][height] = new FactoryNode(width,height);
				mFObjects.add(mFNodes[width][height]);
			}
		}

		//creating mailbox
		mMailbox = new FactoryMailbox(mFactory.getResources());//Mailbox that can store factory resources
		mFObjects.add(mMailbox);//Add this object to the renderer
		mFNodes[0][0].setObject(mMailbox);//Link this object to Node 0,0
		mFNodeMap.put("Mailbox", mFNodes[0][0]);//Make it easy to find the Mailbox node

		//creating stockpersons
		for(int i = 0; i < 3; ++i){
			FactoryStockPersons sp = new FactoryStockPersons(i, mFNodes[0][0],this);
			mFObjects.add(sp);
			mFWorkers.add(sp);
		}
		//reading walls
		Scanner reader = null;
		try {
			reader = new Scanner(new File("walls"));
			while(reader.hasNext()){
				int x = reader.nextInt();
				int y = reader.nextInt();
				String file = reader.next();
				FactoryWall fw = new FactoryWall(new Rectangle(x,y,1,1), file);
				mFObjects.add(fw);
				System.out.println(file+ '\n'+fw.getX() +"   " +fw.getY());
				mFNodes[fw.getX()][fw.getY()].setObject(fw);
			}
		} catch (FileNotFoundException fnfe) {
			// TODO Auto-generated catch block
			System.out.println("Error: reading file in FileSimulation constructor \n" + fnfe.getMessage());
			fnfe.printStackTrace();
		} finally{
			if(reader != null){
				reader.close();
			}
		}
		
		//Link all of the nodes together
		for(FactoryNode[] nodes: mFNodes) {
			for(FactoryNode node : nodes) {
				int x = node.getX();
				int y = node.getY();
				if(x != 0) node.addNeighbor(mFNodes[x-1][y]);
				if(x != mFactory.getWidth()-1) node.addNeighbor(mFNodes[x+1][y]);
				if(y != 0) node.addNeighbor(mFNodes[x][y-1]);
				if(y != mFactory.getHeight()-1) node.addNeighbor(mFNodes[x][y+1]);
			}
		}
		
		//Create the resources
		for(Resource resource : mFactory.getResources()) {
			FactoryResource fr = new FactoryResource(resource);
			mFObjects.add(fr);
			mFNodes[fr.getX()][fr.getY()].setObject(fr);
			mFNodeMap.put(fr.getName(), mFNodes[fr.getX()][fr.getY()]);
		}
		
		//Create the task board
		Point taskBoardLocation = mFactory.getTaskBoardLocation();
		mTaskBoard = new FactoryTaskBoard(inTable,inFactory.getProducts(),taskBoardLocation.x,taskBoardLocation.y);
		mFObjects.add(mTaskBoard);
		mFNodes[taskBoardLocation.x][taskBoardLocation.y].setObject(mTaskBoard);
		mFNodeMap.put("Task Board", mFNodes[taskBoardLocation.x][taskBoardLocation.y]);
		
		//Create the workers, set their first task to look at the task board
		for(int i = 0; i < mFactory.getNumberOfWorkers(); i++) {
			//Start each worker at the first node (upper left corner)
			//Note, may change this, but all factories have an upper left corner(0,0) so it makes sense
			FactoryWorker fw = new FactoryWorker(i, mFNodes[0][0], this);
			mFObjects.add(fw);
			mFWorkers.add(fw);
		}
	}


	public void update(double deltaTime) {
		totalTime += deltaTime;
		if(isDone){return;}
		//Update all the objects in the factor that need updating each tick
		for(FactoryObject object : mFWorkers){
			object.update(deltaTime);
		}


		if(mTaskBoard.isDone()){
			isDone = true;
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			String outFileName = "reports/"+timestamp;
			outFileName = outFileName.replace("[-:. ]", "_");
			FileWriter fw = null;
			File file = null;
			try{
				file = new File(outFileName);
				fw = new FileWriter(outFileName, true);
				file.createNewFile();
				for(FactoryObject object : mFObjects){
					if(object instanceof FactoryReporter){
						((FactoryReporter) object).report(fw);
					}
				}
				DecimalFormat threeDecimals = new DecimalFormat(".###");
				JOptionPane.showMessageDialog(null, "Total time: " + threeDecimals.format(totalTime) + "s",
													"Simulation over!",
													JOptionPane.INFORMATION_MESSAGE);
			}catch(IOException ioe){
				System.out.println("IOExcept on FactorySimulation.update()\n" + ioe.getMessage() +"\n");
				ioe.printStackTrace();
				if(file != null){
					file.delete();
				}
			} finally{
				if(fw != null){
					try{
						fw.close();
					}catch(IOException ioe){
						System.out.println("Error: filed to close FileWriter in FactorySimulation.update() \n" + ioe.getMessage());
						ioe.printStackTrace();
					}
				}
			}
		}
	}

	public FactoryMailbox getmMailbox(){
		return mMailbox;
	}

	public ArrayList<FactoryObject> getObjects() {
		return mFObjects;
	}
	
	public ArrayList<FactoryWorker> getWorkers() {
		return mFWorkers;
	}
	
	public FactoryNode[][] getNodes() {
		return mFNodes;
	}

	public String getName() {
		return mFactory.getName();
	}

	public double getWidth() {
		return mFactory.getWidth();
	}
	
	public double getHeight() {
		return mFactory.getHeight();
	}

	public FactoryNode getNode(String key) {
		return mFNodeMap.get(key);
	}

	public FactoryTaskBoard getTaskBoard() {
		return mTaskBoard;
	}
	
}
