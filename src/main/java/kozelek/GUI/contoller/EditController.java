package kozelek.GUI.contoller;

import kozelek.GUI.Wrapper;
import kozelek.GUI.views.AddWindow;
import kozelek.GUI.views.EditView;
import kozelek.data.GPS;
import kozelek.data.Nehnutelnost;
import kozelek.data.Parcela;
import kozelek.data.objekt.IObject;

public class EditController<T extends IObject> {
    private EditView<T> view;
    private Wrapper wrapper;
    private MainController mainController;

    public EditController(EditView<T> view, Wrapper wrapper, MainController mainController) {
        this.view = view;
        this.wrapper = wrapper;
        this.mainController = mainController;

        this.view.getSubmitEditButton().addActionListener(e -> submitedit());
    }

    private void submitedit() {
        int scislo = Integer.parseInt(this.view.getCisloField().getText());
        String spopis = this.view.getDescField().getText();
        double x1 = Double.parseDouble(this.view.getX1Field().getText());
        double y1 = Double.parseDouble(this.view.getY1Field().getText());
        double x2 = Double.parseDouble(this.view.getX2Field().getText());
        double y2 = Double.parseDouble(this.view.getY2Field().getText());

        boolean button = this.view.getJeNehnutelnostRadio().isSelected();

        T item;
        T item2;
        if (button) {
            item = (T) new Nehnutelnost(scislo, spopis, new GPS(x1, y1));
            this.wrapper.upravNehnutelnost((Nehnutelnost) this.view.getData(), (Nehnutelnost) item);
        } else {
            item = (T) new Parcela(scislo, spopis, new GPS(x1, y1));
            item2 = (T) new Parcela(scislo, spopis, new GPS(x2, y2));
        }


    }


}
