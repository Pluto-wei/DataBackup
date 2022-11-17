package main.view;

import main.DB.DBUtils;
import main.DB.User;
import main.DB.UserInformation;
import main.controller.FileController;
import main.model.FileModel;
import main.model.FileTreeModel;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Enumeration;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
//yonghu1 33
public class UserManagement
{
    private JFrame mainFrame = new JFrame("登录");
    private Container container = mainFrame.getContentPane();
    private JLabel titleLabel = new JLabel("登录/注册", JLabel.CENTER);

    private JPanel inputField = new JPanel();
    private JLabel usernameLabel = new JLabel("用户名:", JLabel.CENTER);
    private JTextField username = new JTextField();
    private JLabel passwordLabel = new JLabel("密码:", JLabel.CENTER);
    private JPasswordField password = new JPasswordField();

    private JPanel buttonField = new JPanel();
    private JButton save = new JButton("登录/注册");
    private JButton cancel = new JButton("取消");

    public UserManagement()
    {
        init();
        setFont(new Font("微软雅黑",Font.PLAIN,14));
        addEvent();
    }

    private void init()
    {
        container.setLayout(new GridLayout(3, 1, 0, 10));
        container.add(titleLabel);

        inputField.setLayout(new GridLayout(2, 2, 5, 5));
        inputField.add(usernameLabel);
        inputField.add(username);
        inputField.add(passwordLabel);
        inputField.add(password);
        container.add(inputField);

        buttonField.setLayout(new FlowLayout(FlowLayout.CENTER,20,0));
        buttonField.add(save);
        buttonField.add(cancel);
        container.add(buttonField);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(300, 200);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private void setFont(Font font)
    {
        FontUIResource fontRes = new FontUIResource(font);
        for(Enumeration<Object> keys = UIManager.getDefaults().keys();keys.hasMoreElements();)
        {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if(value instanceof FontUIResource)
                UIManager.put(key, fontRes);
        }
    }

    private void addEvent()
    {
        save.addActionListener(
            e->
            {
                User user = new User();
                user.setName(username.getText());
                user.setPassword(new String(password.getPassword()));
                if(DBUtils.exists(user)){
                    if(DBUtils.check(user)){
                        new UserInformation(DBUtils.getByName(user.getName()));
                        App app = new App(); // main.view
                        FileModel fileModel = new FileModel(); // file main.model
                        FileTreeModel treeModel = new FileTreeModel(); // file tree main.model
                        FileController fileController = new FileController(app, fileModel, treeModel); // file main.controller
                        app.setVisible(true); // app可见
                        mainFrame.setVisible(false);//登录界面不可见
                    }
                    else{
                        JOptionPane.showConfirmDialog(null,
                                "登录失败", "",JOptionPane.CLOSED_OPTION);
                    }
                }
                else
                    JOptionPane.showConfirmDialog(null,
                    "添加"+(DBUtils.add(user) ? "成功" : "失败"), "",JOptionPane.CLOSED_OPTION);
            }
        );

        cancel.addActionListener(
            e->
            {
                mainFrame.dispose();
            }
        );
    }

    public static void main(String[] args)
    {
        new UserManagement();
    }
}