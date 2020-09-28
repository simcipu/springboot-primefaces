package it.laura.palestra.controller;


import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.IOException;

@Named
@RequestScoped
public class MenuView {

    private MenuModel model;


    @PostConstruct
    public void init() {
        model = new DefaultMenuModel();

        //First submenu
        DefaultSubMenu firstSubmenu = new DefaultSubMenu();
        firstSubmenu.setLabel("Dynamic Submenu");


        DefaultMenuItem item = new DefaultMenuItem();
        item.setValue("External");
        item.setUrl("http://www.primefaces.org");
        item.setIcon("pi pi-home");

        firstSubmenu.getElements().add(item);

        model.getElements().add(firstSubmenu);

        //Second submenu
        DefaultSubMenu secondSubmenu = new DefaultSubMenu();
        secondSubmenu.setLabel("Dynamic Actions");


        item = new DefaultMenuItem();
        item.setValue("Save");
        item.setIcon("pi pi-save");
        item.setCommand("#{menuView.save}");
        item.setUpdate("messages");

        secondSubmenu.getElements().add(item);

        item = new DefaultMenuItem();
        item.setValue("Delete");
        item.setIcon("pi pi-times");
        item.setCommand("#{menuView.delete}");
        item.setAjax(false);

        secondSubmenu.getElements().add(item);

        item = new DefaultMenuItem();
        item.setValue("Redirect");
        item.setIcon("pi pi-search");
        item.setCommand("#{menuView.redirect}");

        secondSubmenu.getElements().add(item);

        model.getElements().add(secondSubmenu);
    }

    public MenuModel getModel() {
        return model;
    }

    public void save() {
        addMessage("Success", "Data saved");
    }

    public void nuovoIscritto()    {

    ExternalContext ec = FacesContext.getCurrentInstance()
            .getExternalContext();
    try {
        ec.redirect(ec.getRequestContextPath()
                + "allievo.xhtml");
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

    }

    public void update() {
        addMessage("Success", "Data updated");
    }

    public void cerca() {
        ExternalContext ec = FacesContext.getCurrentInstance()
                .getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "cerca.xhtml");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        addMessage("Success", "Data deleted");
    }

    public void modifica() {
        ExternalContext ec = FacesContext.getCurrentInstance()
                .getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "modifca.xhtml");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        addMessage("Success", "Data deleted");
    }


    public String view() {
        return "view";
    }
    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}