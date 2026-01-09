package edu.vassar.cmpu203.myfirstapplication.model;

import android.view.View;

public class ButtonItem {

    String itemText;
    String buttonText;
    String buttonDescription;
    boolean buttonExists;
    View.OnClickListener buttonAction;


    public ButtonItem(String itemText, String buttonText, String buttonDescription, boolean buttonExists, View.OnClickListener buttonAction){
        this.itemText = itemText;
        this.buttonText = buttonText;
        this.buttonDescription = buttonDescription;
        this.buttonExists = buttonExists;
        this.buttonAction = buttonAction;
    }

    public String getItemText(){return itemText;}
    public void setItemText(String itemText){this.itemText = itemText;}

    public String getButtonText(){return buttonText;}
    public void setButtonText(String buttonText){this.buttonText = buttonText;}

    public boolean getButtonExists(){return buttonExists;}
    public void setButtonExists(boolean buttonExists){this.buttonExists = buttonExists;}

    public View.OnClickListener getButtonAction(){return buttonAction;}
    public void setButtonAction(View.OnClickListener buttonAction) {this.buttonAction = buttonAction;}

    public String getButtonDescription() {return buttonDescription;}
    public void setButtonDescription(String description) {this.buttonDescription = description;}

}
