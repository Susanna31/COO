package observers;

public interface Observable {

	public void addObserver(Observer o);
	public void updateObservers(String s);
	public void delObserver(Observer o);
}