import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//總共29個城市
public class Main
{
	static City[] cities = new City[29];
	static int[][] distance = new int[29][29];
	
	public static void main(String[] args) throws IOException
	{
		
		readData();
		start();
	}
	//讀取.txt檔案.放進sr2. 在一行一行的讀取, 並將每一行的三個區塊分別放在不同陣列
	static void readData() throws IOException
	{
		String[] s2;
		String s3;
		FileReader fr2 = new FileReader("city.txt");
		BufferedReader sr2 = new BufferedReader(fr2);
		for (int i = 0; i < cities.length; i++)
		{
			s3 = sr2.readLine();
			s2 = s3.split("\\s+");
			cities[i] = new City();
			cities[i].setID(Integer.parseInt(s2[0]));
			cities[i].setX((int) (Double.parseDouble(s2[1])));
			cities[i].setY((int) (Double.parseDouble(s2[2])));

		}
	}
	// 開始計算
	static void start()
	{
		setDistance();
		solve(9, distance);
	}
	
	
	// 避免計算距離時自己到自己,  若[i][j] 距離是0, 則設為極大值,避免都選到自己目前所在城市.
	static void setDistance()
	{
		for(int i=0; i<29;i++)
		{
			for(int j = 0; j<29; j++)
			{				
				distance[i][j] = computeDistance(cities[i], cities[j]);
				if(distance[i][j] == 0)
					distance[i][j] = 9999;
			}
		}
	}
 // 計算距離, 距離相減取平方相加開根號,避免計算出現負號
	private static int computeDistance(City a, City b)
	{
		double x1,x2,y1,y2;
		double distance = 0;
		x1 = a.getX();
		x2 = b.getX();
		y1 = a.getY();
		y2 = b.getY();
		
		distance = Math.sqrt(Math.pow((x1 - x2),2) + Math.pow((y1 - y2), 2));
		return (int)distance;
	}
	
 //計算最短路徑
	static void solve(int point, int[][] data)
	{
		int sum = 0;
		int nextPoint = 0;
		int minPath = 0;
		int initialPoint = point; 
		System.out.println("Depart from node"+(initialPoint+1) + "\n");
		ArrayList<Integer> passed = new ArrayList<Integer>();  //儲存要走哪些城市
		passed.add(initialPoint);
		int[][] points = data;
		
//找出最短的城市
		try
		{
			minPath = points[initialPoint][0];  
			for(int i=0;i < points[initialPoint].length;i++)			
			{
				if(points[initialPoint][i] < minPath)
				{
					minPath = points[initialPoint][i];
					nextPoint = i;	
				}
				else
				{
					continue;
				}
			}
			System.out.println("下一節點:"+(nextPoint+1)+" 路徑長:" + minPath + " 路徑總和:"+ minPath);
			passed.add(nextPoint);
		} 
		catch (ArrayIndexOutOfBoundsException e)
		{	}
		

		try
		{
			while(passed.size() != 29)
			{
				int min = points[nextPoint][0];  //設定下一個點為最短路徑
				int localMin = min ,pointer = 0; //局部最短路徑
				for(int i = 0; i < points[nextPoint].length;i++)
				{
					if(points[nextPoint][i] <= min && passed.contains(i)) 
					{
						if(i+1 != points[nextPoint].length)
							min = points[nextPoint][i+1];
						else
							continue;
						continue;
					}
					else if(points[nextPoint][i] <= min )
					{
						min = points[nextPoint][i];
						pointer = i;
						localMin = min;
					}
					else
					{
						continue;
					}
				}
				minPath += localMin;    //最短路線累加每個局部最短
				nextPoint = pointer;	
				System.out.println("下一節點:"+(nextPoint+1)+" 路徑長:" + localMin + " 路徑總和:"+ minPath); 
				passed.add(nextPoint); 
			}
		}
		catch (ArrayIndexOutOfBoundsException e)
		{	}

	}
	
	
}
