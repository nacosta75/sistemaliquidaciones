/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.web.ui.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Button;

/**
 *
 * @author abraham.acosta
 */
public class ButtonStatusCtrl implements Serializable {
 
    private static final long serialVersionUID = 1L;

	private static enum ButtonEnum {
		Search, Print, First, Previous, Next, Last, New, Edit, Delete, Save, Cancel, Close;
	}

	private final Map<ButtonEnum, Button> buttons = new HashMap<ButtonEnum, Button>(12);

	final private UserWorkspace workspace;

	/** rightName prefix */
	private final String _rightPrefix;

	/**
	 * Var for disable/enable or visible/not visible mode of the butttons. <br>
	 * true = disable the button <br>
	 * false = make the button unvisible<br>
	 */
	private boolean buttonsModeDisable = false;

	/** is the BtnController active ? */
	private boolean active = true;

	/** is the security active ? */
	private boolean securityActive = true;

	/**
	 * Constructor with all possible buttons.
	 * 
	 * @param buttonsModeDisable
	 *            (true = Disable-mode; false = Visible-mode)
	 * @param btnSearch
	 *            (open search dialog Button)
	 * @param btnPrint
	 *            (open print Button)
	 * @param btnFirst
	 *            (Go first record Button)
	 * @param btnPrevious
	 *            (Go previous record Button)
	 * @param btnNext
	 *            (Go next record Button)
	 * @param btnLast
	 *            (Go last record Button)
	 * @param btnNew
	 *            (New Button)
	 * @param btnEdit
	 *            (Edit Button)
	 * @param btnDelete
	 *            (Delete Button)
	 * @param btnSave
	 *            (Save Button)
	 * @param btnCancel
	 *            (Cancel Button)
	 * @param btnClose
	 *            (Close Button)
	 */
	public ButtonStatusCtrl(UserWorkspace userWorkspace, String rightPrefix, boolean buttonsModeDisable, Button btnSearch, Button btnPrint, Button btnFirst, Button btnPrevious, Button btnNext,
			Button btnLast, Button btnNew, Button btnEdit, Button btnDelete, Button btnSave, Button btnCancel, Button btnClose) {
		super();
		this.workspace = userWorkspace;
		this._rightPrefix = rightPrefix + "btn";
		this.buttonsModeDisable = buttonsModeDisable;

		// tools
		buttons.put(ButtonEnum.Search, btnSearch);
		buttons.put(ButtonEnum.Print, btnPrint);
		// navigation
		buttons.put(ButtonEnum.First, btnFirst);
		buttons.put(ButtonEnum.Previous, btnPrevious);
		buttons.put(ButtonEnum.Next, btnNext);
		buttons.put(ButtonEnum.Last, btnLast);
		// crud
		buttons.put(ButtonEnum.New, btnNew);
		buttons.put(ButtonEnum.Edit, btnEdit);
		buttons.put(ButtonEnum.Delete, btnDelete);
		buttons.put(ButtonEnum.Save, btnSave);
		buttons.put(ButtonEnum.Cancel, btnCancel);
		buttons.put(ButtonEnum.Close, btnClose);

		setBtnImages();
		setBtnToolTiptext();
	}

	/**
	 * Constructor with CLOSE button.
	 * 
	 * @param buttonsModeDisable
	 *            (true = Disable-mode; false = Visible-mode)
	 * @param btnNew
	 *            (New Button)
	 * @param btnEdit
	 *            (Edit Button)
	 * @param btnDelete
	 *            (Delete Button)
	 * @param btnSave
	 *            (Save Button)
	 * @param btnCancel
	 *            (Cancel Button)
	 * @param btnClose
	 *            (Close Button)
	 */
	public ButtonStatusCtrl(UserWorkspace userWorkspace, String rightPrefix, boolean buttonsModeDisable, Button btnNew, Button btnEdit, Button btnDelete, Button btnSave, Button btnCancel,
			Button btnClose) {
		super();
		this.workspace = userWorkspace;
		this._rightPrefix = rightPrefix + "btn";
		this.buttonsModeDisable = buttonsModeDisable;

		buttons.put(ButtonEnum.New, btnNew);
		buttons.put(ButtonEnum.Edit, btnEdit);
		buttons.put(ButtonEnum.Delete, btnDelete);
		buttons.put(ButtonEnum.Save, btnSave);
		buttons.put(ButtonEnum.Cancel, btnCancel);
		buttons.put(ButtonEnum.Close, btnClose);

		setBtnImages();
		setBtnToolTiptext();
	}

	/**
	 * Set the images for the buttons.<br>
	 */
	private void setBtnImages() {
		String imagePath = "/images/icons/";

		if (ButtonEnum.Search != null)
			setImage(ButtonEnum.Search, imagePath + "btn_search2_16x16.gif");
		if (ButtonEnum.Print != null)
			setImage(ButtonEnum.Print, imagePath + "btn_print2_16x16.gif");
		if (ButtonEnum.First != null)
			setImage(ButtonEnum.First, imagePath + "btn_first_16x16.png");
		if (ButtonEnum.Previous != null)
			setImage(ButtonEnum.Previous, imagePath + "btn_previous_16x16.png");
		if (ButtonEnum.Next != null)
			setImage(ButtonEnum.Next, imagePath + "btn_next_16x16.png");
		if (ButtonEnum.Last != null)
			setImage(ButtonEnum.Last, imagePath + "btn_last_16x16.png");
		if (ButtonEnum.New != null)
			setImage(ButtonEnum.New, imagePath + "btn_new2_16x16.gif");
		if (ButtonEnum.Edit != null)
			setImage(ButtonEnum.Edit, imagePath + "btn_edit2_16x16.gif");
		if (ButtonEnum.Delete != null)
			setImage(ButtonEnum.Delete, imagePath + "btn_delete2_16x16.gif");
		if (ButtonEnum.Save != null)
			setImage(ButtonEnum.Save, imagePath + "btn_save2_16x16.gif");
		if (ButtonEnum.Cancel != null)
			setImage(ButtonEnum.Cancel, imagePath + "btn_cancel2_16x16.gif");
		if (ButtonEnum.Close != null)
			setImage(ButtonEnum.Close, imagePath + "btn_exitdoor2_16x16.gif");
	}

	/**
	 * Set the ToolTip text for the buttons.<br>
	 */
	private void setBtnToolTiptext() {

		if (ButtonEnum.Search != null)
			setTooltipText(ButtonEnum.Search, Labels.getLabel("btnSearch.tooltiptext"));
		if (ButtonEnum.Print != null)
			setTooltipText(ButtonEnum.Print, Labels.getLabel("btnPrint.tooltiptext"));
		if (ButtonEnum.First != null)
			setTooltipText(ButtonEnum.First, Labels.getLabel("btnFirst.tooltiptext"));
		if (ButtonEnum.Previous != null)
			setTooltipText(ButtonEnum.Previous, Labels.getLabel("btnPrevious.tooltiptext"));
		if (ButtonEnum.Next != null)
			setTooltipText(ButtonEnum.Next, Labels.getLabel("btnNext.tooltiptext"));
		if (ButtonEnum.Last != null)
			setTooltipText(ButtonEnum.Last, Labels.getLabel("btnLast.tooltiptext"));
		if (ButtonEnum.New != null)
			setTooltipText(ButtonEnum.New, Labels.getLabel("btnNew.tooltiptext"));
		if (ButtonEnum.Edit != null)
			setTooltipText(ButtonEnum.Edit, Labels.getLabel("btnEdit.tooltiptext"));
		if (ButtonEnum.Delete != null)
			setTooltipText(ButtonEnum.Delete, Labels.getLabel("btnDelete.tooltiptext"));
		if (ButtonEnum.Save != null)
			setTooltipText(ButtonEnum.Save, Labels.getLabel("btnSave.tooltiptext"));
		if (ButtonEnum.Cancel != null)
			setTooltipText(ButtonEnum.Cancel, Labels.getLabel("btnCancel.tooltiptext"));
		if (ButtonEnum.Close != null)
			setTooltipText(ButtonEnum.Close, Labels.getLabel("btnClose.tooltiptext"));
	}

	/**
	 * Set all Buttons for the Mode if 'NEW' is pressed. <br>
	 */
	public void setBtnStatus_New() {
		if (buttonsModeDisable) {
			if (ButtonEnum.Search != null)
				setDisabled(ButtonEnum.Search, true);
			if (ButtonEnum.Print != null)
				setDisabled(ButtonEnum.Print, true);
			if (ButtonEnum.First != null)
				setDisabled(ButtonEnum.First, true);
			if (ButtonEnum.Previous != null)
				setDisabled(ButtonEnum.Previous, true);
			if (ButtonEnum.Next != null)
				setDisabled(ButtonEnum.Next, true);
			if (ButtonEnum.Last != null)
				setDisabled(ButtonEnum.Last, true);
			if (ButtonEnum.New != null)
				setDisabled(ButtonEnum.New, true);
			if (ButtonEnum.Edit != null)
				setDisabled(ButtonEnum.Edit, true);
			if (ButtonEnum.Delete != null)
				setDisabled(ButtonEnum.Delete, true);
			if (ButtonEnum.Save != null)
				setDisabled(ButtonEnum.Save, false);
			if (ButtonEnum.Cancel != null)
				setDisabled(ButtonEnum.Cancel, false);
			if (ButtonEnum.Close != null)
				setDisabled(ButtonEnum.Close, false);
		} else {
			if (ButtonEnum.Search != null)
				setVisible(ButtonEnum.Search, false);
			if (ButtonEnum.Print != null)
				setVisible(ButtonEnum.Print, false);
			if (ButtonEnum.First != null)
				setVisible(ButtonEnum.First, false);
			if (ButtonEnum.Previous != null)
				setVisible(ButtonEnum.Previous, false);
			if (ButtonEnum.Next != null)
				setVisible(ButtonEnum.Next, false);
			if (ButtonEnum.Last != null)
				setVisible(ButtonEnum.Last, false);
			if (ButtonEnum.New != null)
				setVisible(ButtonEnum.New, false);
			if (ButtonEnum.Edit != null)
				setVisible(ButtonEnum.Edit, false);
			if (ButtonEnum.Delete != null)
				setVisible(ButtonEnum.Delete, false);
			if (ButtonEnum.Save != null)
				setVisible(ButtonEnum.Save, true);
			if (ButtonEnum.Cancel != null)
				setVisible(ButtonEnum.Cancel, true);
			if (ButtonEnum.Close != null)
				setVisible(ButtonEnum.Close, true);
		}
	}

	/**
	 * Set all Buttons for the Mode if 'EDIT' is pressed. <br>
	 */
	public void setBtnStatus_Edit() {
		if (buttonsModeDisable) {
			if (ButtonEnum.Search != null)
				setDisabled(ButtonEnum.Search, true);
			if (ButtonEnum.Print != null)
				setDisabled(ButtonEnum.Print, true);
			if (ButtonEnum.First != null)
				setDisabled(ButtonEnum.First, true);
			if (ButtonEnum.Previous != null)
				setDisabled(ButtonEnum.Previous, true);
			if (ButtonEnum.Next != null)
				setDisabled(ButtonEnum.Next, true);
			if (ButtonEnum.Last != null)
				setDisabled(ButtonEnum.Last, true);
			if (ButtonEnum.New != null)
				setDisabled(ButtonEnum.New, true);
			if (ButtonEnum.Edit != null)
				setDisabled(ButtonEnum.Edit, true);
			if (ButtonEnum.Delete != null)
				setDisabled(ButtonEnum.Delete, true);
			if (ButtonEnum.Save != null)
				setDisabled(ButtonEnum.Save, false);
			if (ButtonEnum.Cancel != null)
				setDisabled(ButtonEnum.Cancel, false);
			if (ButtonEnum.Close != null)
				setDisabled(ButtonEnum.Close, false);
		} else {
			if (ButtonEnum.Search != null)
				setVisible(ButtonEnum.Search, false);
			if (ButtonEnum.Print != null)
				setVisible(ButtonEnum.Print, false);
			if (ButtonEnum.First != null)
				setVisible(ButtonEnum.First, false);
			if (ButtonEnum.Previous != null)
				setVisible(ButtonEnum.Previous, false);
			if (ButtonEnum.Next != null)
				setVisible(ButtonEnum.Next, false);
			if (ButtonEnum.Last != null)
				setVisible(ButtonEnum.Last, false);
			if (ButtonEnum.New != null)
				setVisible(ButtonEnum.New, false);
			if (ButtonEnum.Edit != null)
				setVisible(ButtonEnum.Edit, false);
			if (ButtonEnum.Delete != null)
				setVisible(ButtonEnum.Delete, false);
			if (ButtonEnum.Save != null)
				setVisible(ButtonEnum.Save, true);
			if (ButtonEnum.Cancel != null)
				setVisible(ButtonEnum.Cancel, true);
			if (ButtonEnum.Close != null)
				setVisible(ButtonEnum.Close, true);
		}
	}

	/**
	 * Not needed yet, because after pressed the delete button <br>
	 * the window is closing. <br>
	 */
	public void setBtnStatus_Delete() {
	}

	/**
	 * Set all Buttons for the Mode SAVE is pressed. <br>
	 */
	public void setBtnStatus_Save() {
		setInitEdit();
	}

	/**
	 * Set all Buttons for the Mode init in EDIT mode. <br>
	 * This means that the Dialog window is opened and <br>
	 * shows data. <br>
	 */
	public void setInitEdit() {
		if (buttonsModeDisable) {
			if (ButtonEnum.Search != null)
				setDisabled(ButtonEnum.Search, false);
			if (ButtonEnum.Print != null)
				setDisabled(ButtonEnum.Print, false);
			if (ButtonEnum.First != null)
				setDisabled(ButtonEnum.First, false);
			if (ButtonEnum.Previous != null)
				setDisabled(ButtonEnum.Previous, false);
			if (ButtonEnum.Next != null)
				setDisabled(ButtonEnum.Next, false);
			if (ButtonEnum.Last != null)
				setDisabled(ButtonEnum.Last, false);
			if (ButtonEnum.New != null)
				setDisabled(ButtonEnum.New, false);
			if (ButtonEnum.Edit != null)
				setDisabled(ButtonEnum.Edit, false);
			if (ButtonEnum.Delete != null)
				setDisabled(ButtonEnum.Delete, false);
			if (ButtonEnum.Save != null)
				setDisabled(ButtonEnum.Save, true);
			if (ButtonEnum.Cancel != null)
				setDisabled(ButtonEnum.Cancel, true);
			if (ButtonEnum.Close != null)
				setDisabled(ButtonEnum.Close, false);
		} else {
			if (ButtonEnum.Search != null)
				setVisible(ButtonEnum.Search, true);
			if (ButtonEnum.Print != null)
				setVisible(ButtonEnum.Print, true);
			if (ButtonEnum.First != null)
				setVisible(ButtonEnum.First, true);
			if (ButtonEnum.Previous != null)
				setVisible(ButtonEnum.Previous, true);
			if (ButtonEnum.Next != null)
				setVisible(ButtonEnum.Next, true);
			if (ButtonEnum.Last != null)
				setVisible(ButtonEnum.Last, true);
			if (ButtonEnum.New != null)
				setVisible(ButtonEnum.New, true);
			if (ButtonEnum.Edit != null)
				setVisible(ButtonEnum.Edit, true);
			if (ButtonEnum.Delete != null)
				setVisible(ButtonEnum.Delete, true);
			if (ButtonEnum.Save != null)
				setVisible(ButtonEnum.Save, false);
			if (ButtonEnum.Cancel != null)
				setVisible(ButtonEnum.Cancel, false);
			if (ButtonEnum.Close != null)
				setVisible(ButtonEnum.Close, true);
		}
	}

	/**
	 * Set all Buttons for the Mode init in NEW mode. <br>
	 * This means that the Dialog window is freshly new <br>
	 * and have no data. <br>
	 */
	public void setInitNew() {
		if (buttonsModeDisable) {
			if (ButtonEnum.Search != null)
				setDisabled(ButtonEnum.Search, true);
			if (ButtonEnum.Print != null)
				setDisabled(ButtonEnum.Print, true);
			if (ButtonEnum.First != null)
				setDisabled(ButtonEnum.First, true);
			if (ButtonEnum.Previous != null)
				setDisabled(ButtonEnum.Previous, true);
			if (ButtonEnum.Next != null)
				setDisabled(ButtonEnum.Next, true);
			if (ButtonEnum.Last != null)
				setDisabled(ButtonEnum.Last, true);
			if (ButtonEnum.New != null)
				setDisabled(ButtonEnum.New, true);
			if (ButtonEnum.Edit != null)
				setDisabled(ButtonEnum.Edit, true);
			if (ButtonEnum.Delete != null)
				setDisabled(ButtonEnum.Delete, true);
			if (ButtonEnum.Save != null)
				setDisabled(ButtonEnum.Save, false);
			if (ButtonEnum.Cancel != null)
				setDisabled(ButtonEnum.Cancel, false);
			if (ButtonEnum.Close != null)
				setDisabled(ButtonEnum.Close, false);
		} else {
			if (ButtonEnum.Search != null)
				setVisible(ButtonEnum.Search, false);
			if (ButtonEnum.Print != null)
				setVisible(ButtonEnum.Print, false);
			if (ButtonEnum.First != null)
				setVisible(ButtonEnum.First, false);
			if (ButtonEnum.Previous != null)
				setVisible(ButtonEnum.Previous, false);
			if (ButtonEnum.Next != null)
				setVisible(ButtonEnum.Next, false);
			if (ButtonEnum.Last != null)
				setVisible(ButtonEnum.Last, false);
			if (ButtonEnum.New != null)
				setVisible(ButtonEnum.New, false);
			if (ButtonEnum.Edit != null)
				setVisible(ButtonEnum.Edit, false);
			if (ButtonEnum.Delete != null)
				setVisible(ButtonEnum.Delete, false);
			if (ButtonEnum.Save != null)
				setVisible(ButtonEnum.Save, true);
			if (ButtonEnum.Cancel != null)
				setVisible(ButtonEnum.Cancel, true);
			if (ButtonEnum.Close != null)
				setVisible(ButtonEnum.Close, true);
		}
	}

	/**
	 * Sets the image of a button.<br>
	 * 
	 * @param b
	 * @param imagePath
	 *            path and image name
	 */
	private void setImage(ButtonEnum b, String imagePath) {
		if (buttons.get(b) != null) {
			buttons.get(b).setImage(imagePath);
		}
	}

	/**
	 * Sets the image of a button.<br>
	 * 
	 * @param b
	 * @param imagePath
	 *            path and image name
	 */
	private void setTooltipText(ButtonEnum b, String tooltip) {
		if (buttons.get(b) != null) {
			buttons.get(b).setTooltiptext(tooltip);
		}
	}

	/**
	 * Set the button visible.<br>
	 * 
	 * @param b
	 * @param visible
	 *            True or False
	 */
	private void setVisible(ButtonEnum b, boolean visible) {

		// check first if the ButtonController is active
		if (isActive()) {

			// check if the button is declared
			if (buttons.get(b) != null) {

				if (visible) {
					if (isSecurityActive()) {
						if (workspace.isAllowed(_rightPrefix + b.name())) {
							buttons.get(b).setVisible(visible);
						}
					} else
						buttons.get(b).setVisible(visible);
				} else {
					buttons.get(b).setVisible(visible);
				}
			}
		}
	}

	/**
	 * Sets the button disabled.<br>
	 * 
	 * @param b
	 * @param disabled
	 *            True or False
	 */
	private void setDisabled(ButtonEnum b, boolean disabled) {

		// check first if the ButtonController is active
		if (isActive()) {

			// check if the button is declared
			if (buttons.get(b) != null) {

				if (disabled) {
					buttons.get(b).setDisabled(disabled);
				} else {
					if (isSecurityActive()) {
						if (workspace.isAllowed(_rightPrefix + b.name())) {
							buttons.get(b).setDisabled(disabled);
						}
					} else
						buttons.get(b).setDisabled(disabled);
				}
			}
		}
	}

	/**
	 * Sets all buttons disabled/visible.<br>
	 * 
	 * @param activate
	 *            True or False
	 */
	@SuppressWarnings("unchecked")
	public void setActivateAll(boolean activate) {

		Iterator it = buttons.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			// System.out.println(pairs.getKey() + " = " + pairs.getValue());

			if ((Button) pairs.getValue() != null) {
				if (buttonsModeDisable == true)
					((Button) pairs.getValue()).setDisabled(!activate);
				else if (buttonsModeDisable == false)
					((Button) pairs.getValue()).setVisible(activate);
			}

		}

		setActive(activate);
	}

	/**
	 * Set this ButtonController active. <br>
	 * Means show the Buttons.<br>
	 * 
	 * @param active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Is this ButtonController active? <br>
	 * Means does it shows the Buttons? <br>
	 * 
	 * @return
	 */
	public boolean isActive() {
		return active;
	}

	public void setSecurityActive(boolean securityActive) {
		this.securityActive = securityActive;
	}

	/**
	 * Is this ButtonController security active? <br>
	 * Means does it checks for rights? <br>
	 * 
	 * @return
	 */
	public boolean isSecurityActive() {
		return securityActive;
	}
}
