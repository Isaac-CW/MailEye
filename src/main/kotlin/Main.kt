import MailViewer.Application
import MailViewer.authorization.*
import MailViewer.connection.*
import MailViewer.mail.*
import MailViewer.pagination.PageManager

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

import com.sun.mail.imap.IMAPFolder
import jakarta.mail.Folder
import jakarta.mail.Store

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        Button(onClick = {
            text = "Hello, Desktop!"
        }) {
            Text(text)
        }
    }
}

fun main() = application {
    var app:Application = Application.getInstance();
    var test:GoogleURLs = GoogleURLs();

    var g:EmailAccount = EmailAccount(
        app.mailboxes.getAccess(Mailbox.GMAIL),
        GmailURL(),
        "IsaacLoCW@gmail.com"
    );

    var acc:Store = g.connectImap();
    acc.use {
        val def = acc.getFolder("INBOX") as IMAPFolder;

        def.open(Folder.READ_ONLY);

        val pm = PageManager(def)
        pm.setPageSize(20)
        val currentPage = pm.jumpToPage(-1)
    }

    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
