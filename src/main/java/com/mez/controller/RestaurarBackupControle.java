package com.mez.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.event.FileUploadEvent;

@ManagedBean
@SessionScoped
public class RestaurarBackupControle implements Serializable {

	private static final long serialVersionUID = 1L;
	private String lblMessage;
	ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	String basePath = ctx.getRealPath("");

	private String txtPath = basePath + "backup";

	public void upload(FileUploadEvent event) {

		if (event.getFile().getFileName().equals("")) {
			lblMessage = "Please Select file to restore!";
			System.out.println(lblMessage);
		} else {
			restoreDB("root", "root", txtPath + "/" + event.getFile().getFileName());
			System.out.println(txtPath + "/" + event.getFile().getFileName());
		}
	}

	public boolean restoreDB(String dbUserName, String dbPassword, String source) {

		// try {
		// DropDatabase.executa();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// String[] executeCmd = new String[]{"C:\\Program Files\\MySQL\\MySQL
		// Server 5.0\\bin\\mysql ", "--user=" + dbUserName, "--password=" +
		// dbPassword, "-e", "source " + source};
		String[] executeCmd = new String[] { "C:\\Program Files\\MySQL\\MySQL Server 5.6\\bin\\mysql ", "--user=" + dbUserName, "--password=" + dbPassword,
				"-e", "source " + source };

		Process runtimeProcess;
		try {

			runtimeProcess = Runtime.getRuntime().exec(executeCmd);
			int processComplete = runtimeProcess.waitFor();

			if (processComplete == 0) {
				lblMessage = "Backup restored successfully!";
				System.out.println(lblMessage);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO.toString(), "Backup restaurado com Sucesso!"));

				return true;
			} else {
				lblMessage = "Could not restore the backup!";
				System.out.println(lblMessage);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO.toString(),
								"Backup não restaurado, favor entrar em contato com o Administrador!"));

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return false;
	}
}
