package bc.bcCommunicator.Views;

public interface ILetterViewFactory {
	ILetterView getLetterView(String username, String inLetterText, String dateText, boolean alignLeft);
}
