package finki.ask.view;

public class View {
	public interface Public {};
	
	public interface SummaryAPI extends Public {};
	public interface SummaryAdmin extends SummaryAPI {};
	
	public interface CompleteAPI extends SummaryAPI {};
	public interface CompleteAdmin extends CompleteAPI, SummaryAdmin {};
	
}
