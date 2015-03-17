package se.trifork.intellij.whitey;

public class RemoveButOneWhitespaceAction extends WhitespaceReplacerAction {

    @Override
    protected String getReplacement() {
        return " ";
    }
}
