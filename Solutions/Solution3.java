import java.util.*;

public class Main
{
	public static void main(String[] args)
	{
		solution(new int[]{0,0,1,4}, new int[]{0,1,1,0},4);
	}
	public static void solution(int[] arrival, int[] street, int n)
	{
		Deque<int[]> time_car=new ArrayDeque<>();
		Deque<int[]> main_street=new ArrayDeque<>();
		Deque<int[]> first_avenue=new ArrayDeque<>();
		int[] ans=new int[n];
		
		for(int i=0;i<n;i++)    time_car.add(new int[]{arrival[i],i});
		
		int cur_time=0,pre_car=-1;
		int[] cur_time_car=new int[2];
		while(!time_car.isEmpty() || !main_street.isEmpty() || !first_avenue.isEmpty())
		{
			if(main_street.isEmpty() && first_avenue.isEmpty())
			{
				if(time_car.peek()[0] != cur_time)          pre_car=-1;
				cur_time=time_car.peek()[0];
			}
			
			while(!time_car.isEmpty() && time_car.peek()[0]==cur_time)
			{
				cur_time_car=time_car.poll();
				
				if(street[cur_time_car[1]]==0)           main_street.add(cur_time_car);
				else if(street[cur_time_car[1]]==1)      first_avenue.add(cur_time_car);
			}
			
			if((pre_car==-1 || pre_car==1 || main_street.isEmpty()) && !first_avenue.isEmpty())
			{
				int[] cur_first_avenue=first_avenue.poll();
				ans[cur_first_avenue[1]]=cur_time++;
				pre_car=1;
			}
			else if((pre_car==0 || first_avenue.isEmpty()) && !main_street.isEmpty())
			{
				int[] cur_main_street=main_street.poll();
				ans[cur_main_street[1]]=cur_time++;
				pre_car=0;
			}
		}
		
		for (int i = 0; i < ans.length; i++)    System.out.println(ans[i]);
	}
}
