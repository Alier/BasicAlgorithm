import java.util.Comparator;


public class reverseComparator implements Comparator<Integer> {

	@Override
	public int compare(Integer x , Integer y) {
		// TODO Auto-generated method stub
		return(Integer.compare(y, x));
	}

}
