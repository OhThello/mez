package com.mez.controller;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

@ManagedBean
@ViewScoped
public class BackupControle implements Serializable{

	private static final long serialVersionUID = 1L;
	ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	String basePath = ctx.getRealPath("");

	private String localSalvar = basePath + "Backup";
	private String lblMessage;

	public void backupMysql() {
		if (localSalvar.equals("")) {
			lblMessage = ("Please choose path to save!");
			System.out.println(lblMessage);
		} else {
			Date now = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
			String nomeArquivo = dateFormat.format(now);

			System.out.println(nomeArquivo);
			criarCaminho();
			String mysql = "C:\\Program Files\\MySQL\\MySQL Server 5.6\\bin\\mysqldump -uroot -proot --add-drop-database -B teste -r " + "\"" + localSalvar
					+ "/" + nomeArquivo + ".sql\"";
			System.out.println(mysql);
			Process p = null;
			try {
				Runtime runtime = Runtime.getRuntime();
				p = runtime.exec(mysql);

				int processComplete = p.waitFor();

				if (processComplete == 0) {
					lblMessage = "Backup efetuado com sucesso em: " + localSalvar + "\\" + nomeArquivo + ".sql";
					System.out.println(lblMessage);
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO.toString(), "Backup efetuado com Sucesso!"));
				} else {
					lblMessage = "Could not create the backup";
					System.out.println(lblMessage);
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO.toString(),
									"Backup não efetuado, entre em contato com o Administrador!"));

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void backupPost() throws IOException, InterruptedException {
		localSalvar = "D:\\PgDump";
		if (localSalvar.equals("")) {
			lblMessage = ("Please choose path to save!");
			System.out.println(lblMessage);
		} else {
			Date now = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
			String nomeArquivo = dateFormat.format(now);

			System.out.println(nomeArquivo);
			criarCaminho();
			String post = "D:/PgDump pg_dump -d backuptest -U postgres  > D:\\PgDump\\teste.sql";
			System.out.println(post);
			Process p = null;
			try {
				Runtime runtime = Runtime.getRuntime();
				p = runtime.exec(post);
				runtime.exec("root");

				int processComplete = p.waitFor();

				if (processComplete == 0) {
					lblMessage = "Backup efetuado com sucesso em: " + localSalvar + "\\" + nomeArquivo + ".sql";
					System.out.println(lblMessage);
				} else {
					lblMessage = "Could not create the backup";
					System.out.println(lblMessage);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void criarCaminho() {
		File file = new File(localSalvar);
		if (!file.exists()) {
			file.mkdirs();
		}
	}
}
