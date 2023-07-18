import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerChat extends JFrame implements  Runnable {
    JTextArea ta;
    JPanel p1;
    JTextField t1;
    JButton b1;
    static DataOutputStream dos;
    static DataInputStream dis;
    ServerChat()
    {
        Font f = new Font("Verdana",Font.BOLD,28);
        ta = new JTextArea();
        p1 = new JPanel();
        t1 = new JTextField(20);
        b1 = new JButton("Send");
        p1.setLayout(new FlowLayout());
        p1.add(t1);
        p1.add(b1);
        setLayout(new GridLayout(2,1));
        add(ta);
        add(p1);
        ta.setFont(f);
        t1.setFont(f);
        b1.setFont(f);
        ActionListener send = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try
                {
                    String r = t1.getText();
                    dos.writeUTF(r);
                    ta.append("Server : " + r +"\n");
                    t1.setText("");

                }
                catch (Exception ex)
                {
                    System.out.println(ex);
                }
            }
        };
        b1.addActionListener(send);
    }

    public void run()
    {
        try {
            while (true) {
                String r = dis.readUTF();
                ta.append("Client :  "+r+"\n");
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }
    public static void main(String[] args) {

        try {
            ServerSocket st = new ServerSocket(8765);
            Socket s = st.accept();
            dos =new DataOutputStream(s.getOutputStream());
            dis = new DataInputStream(s.getInputStream());


            ServerChat x = new ServerChat();
            Thread t = new Thread(x);
            t.start();
            x.setVisible(true);
            x.setSize(750, 400);
            x.setTitle("Server App");
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
    }
}
