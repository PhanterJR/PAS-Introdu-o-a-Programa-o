import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Pas extends JFrame{
    /**
     * @version 1.0;
     * @author Claudionor Júnior Nascimento
     */
    private static final long serialVersionUID = 1L;
    private ArrayList<String> novos_envolvidos;
    private ArrayList<String> lista_ocorrencias;
    private ArrayList<String> lista_locais;
    private ArrayList<String[]> dados_da_tabela;
    private JTable tabela;
    private JPanel panel;
    private JLabel lbl_envolvidos;
    private JComboBox<String> cbox_ocorrencia;
    private JComboBox<String> cbox_local;
    private String conteudo_arquivo;
    private JButton btn_salvar_lista;
    public void Relatorios(int relatorio){
        JFrame frame_relatorio = new JFrame();
        ImageIcon icon_info = new ImageIcon("imagens/info.png");
        ImageIcon icon_relatorio = new ImageIcon("imagens/relatorio.png");      
        frame_relatorio.setSize(600, 500);
        frame_relatorio.setMinimumSize(new Dimension(500, 500));
        frame_relatorio.setLocationRelativeTo(null);
        GridBagConstraints cons = new GridBagConstraints(); 
        GridBagLayout layout = new GridBagLayout();
        JTextPane txt_relatorio = new JTextPane();
        StyledDocument meu_relatorio = (StyledDocument) txt_relatorio.getDocument();

        SimpleAttributeSet normal = new SimpleAttributeSet();
        StyleConstants.setFontFamily(normal, "SansSerif");
        StyleConstants.setFontSize(normal, 16);
        StyleConstants.setFirstLineIndent(normal, 0);

        SimpleAttributeSet seminormal = new SimpleAttributeSet(normal);
        StyleConstants.setBold(seminormal, true);
        StyleConstants.setFirstLineIndent(normal, 30);

        SimpleAttributeSet highAlert = new SimpleAttributeSet(seminormal);
        StyleConstants.setFontSize(highAlert, 18);
        StyleConstants.setItalic(highAlert, true);
        StyleConstants.setForeground(highAlert, Color.blue);
        StyleConstants.setFirstLineIndent(highAlert, 30);

        txt_relatorio.setEditable(false);
        JScrollPane scrollPanel = new JScrollPane(txt_relatorio);
        panel = new JPanel();
        panel.setLayout(layout);
        frame_relatorio.add(panel);
        cons.insets = new Insets(10,10,10,10);
        cons.weightx = 1;
        cons.weighty = 1;
        cons.gridy = 0;
        cons.gridx = 0;
        cons.gridwidth = 1; 
        cons.gridheight = 1;
        cons.anchor = GridBagConstraints.NORTH;
        cons.fill = GridBagConstraints.BOTH;

        panel.add(scrollPanel, cons);

        conteudo_arquivo = new String();
        dados_da_tabela = new ArrayList<String[]>();
        LerArquivo();
        String relatorio_1 = "";
        String relatorio_2 = "";
        String relatorio_2_2 = "";
        String relatorio_3 = "";
        String relatorio_4 = "";
        Boolean relatorio_2_ok = true;
        ArrayList<String> lc_ocorrencias = new ArrayList<String>();
        ArrayList<String> lc_local = new ArrayList<String>();
        ArrayList<String> lc_descricao = new ArrayList<String>();
        ArrayList<String> lc_envolvidos = new ArrayList<String>();
        ArrayList<String> set_ocorrencias = new ArrayList<String>();
        ArrayList<Integer> tot_ocorrencias = new ArrayList<Integer>();
        ArrayList<String> set_local = new ArrayList<String>();
        ArrayList<Integer> tot_local = new ArrayList<Integer>();
        Map<String, Integer> dicionario1 = new HashMap<String, Integer>();
        Map<String, Integer> dicionario2 = new HashMap<String, Integer>();
        double total_de_ocorrencias = dados_da_tabela.size();
        for (int i = 0; i < dados_da_tabela.size(); i++) {
            lc_ocorrencias.add(dados_da_tabela.get(i)[0]);
            lc_local.add(dados_da_tabela.get(i)[1]);
            lc_descricao.add(dados_da_tabela.get(i)[2]);
            lc_envolvidos.add(dados_da_tabela.get(i)[3]);
            if (set_ocorrencias.contains(dados_da_tabela.get(i)[0])){
                int pos_oco = set_ocorrencias.indexOf(dados_da_tabela.get(i)[0]);
                tot_ocorrencias.set(pos_oco, tot_ocorrencias.get(pos_oco)+1);
                int temp_oco=dicionario1.get(dados_da_tabela.get(i)[0]);
                dicionario1.put(dados_da_tabela.get(i)[0], new Integer(temp_oco+1));

            } else {
                tot_ocorrencias.add(1);             
                set_ocorrencias.add(dados_da_tabela.get(i)[0]);
                dicionario1.put(dados_da_tabela.get(i)[0], new Integer(1));
            }
            if (set_local.contains(dados_da_tabela.get(i)[1])){
                int pos_loc = set_local.indexOf(dados_da_tabela.get(i)[1]);
                tot_local.set(pos_loc, tot_local.get(pos_loc)+1);
                int temp_loc=dicionario2.get(dados_da_tabela.get(i)[1]);
                dicionario2.put(dados_da_tabela.get(i)[1], new Integer(temp_loc+1));
            } else {
                tot_local.add(1);
                set_local.add(dados_da_tabela.get(i)[1]);
                dicionario2.put(dados_da_tabela.get(i)[1], new Integer(1));
            }
            if (relatorio==2){
                int cont_er2=i+1;
                relatorio_2="   "+cont_er2+" - "+dados_da_tabela.get(i)[0]+"\n";
                relatorio_2_2="       - "+dados_da_tabela.get(i)[3]+".\n\n";
                try{
                    meu_relatorio.insertString(meu_relatorio.getLength(), relatorio_2, highAlert);
                    meu_relatorio.insertString(meu_relatorio.getLength(), relatorio_2_2, normal);
                } catch(BadLocationException badLocationException) {
                    relatorio_2_ok = false;
                }

            }
        }
        if(relatorio==1){
            frame_relatorio.setIconImage(icon_relatorio.getImage());
            ArrayList<Integer> sort_ocorrencias = new ArrayList<Integer>(tot_ocorrencias);
            Collections.sort(sort_ocorrencias, Collections.reverseOrder());
            int cont_or=0;
            for(int z = 0; z < 5; z++){
                for(int x = 0; x < tot_ocorrencias.size(); x++){
                    if (sort_ocorrencias.get(z)==tot_ocorrencias.get(x)) {
                        cont_or++;
                        relatorio_1=relatorio_1+cont_or+" - "+set_ocorrencias.get(x)+" com "+sort_ocorrencias.get(z)+" Ocorrência(s)\n";
                        tot_ocorrencias.remove(x);
                        set_ocorrencias.remove(x);
                    }
                }
            }
            try {
                meu_relatorio.insertString(meu_relatorio.getLength(), relatorio_1 + "\n", highAlert);
                meu_relatorio.setParagraphAttributes(0,meu_relatorio.getLength(),highAlert, false);
            } catch (BadLocationException badLocationException) {
                txt_relatorio.setText(relatorio_1);
            }

            frame_relatorio.setTitle("Cinco ocorrências que mais se repetem");
            frame_relatorio.setVisible(true);
        } else if (relatorio==2) {
            frame_relatorio.setIconImage(icon_relatorio.getImage());
            if(relatorio_2_ok){

            }else{          
                txt_relatorio.setText(relatorio_2);
            }

            frame_relatorio.setTitle("Lista de Ocorrências e seus envolvidos");
            frame_relatorio.setVisible(true);           
        } else if (relatorio==3) {
            frame_relatorio.setIconImage(icon_relatorio.getImage());
            ArrayList<Integer> sort_percentuais = new ArrayList<Integer>(tot_ocorrencias);
            Collections.sort(sort_percentuais, Collections.reverseOrder());
            int cont_per=0;
            for(int z = 0; z < sort_percentuais.size(); z++){
                for(int x = 0; x < tot_ocorrencias.size(); x++){
                    if (sort_percentuais.get(z)==tot_ocorrencias.get(x)) {
                        cont_per++;
                        double porcentagem = ((sort_percentuais.get(z)/total_de_ocorrencias)*100.0);
                        String porcentagem_formatado=String.format("%.2f",porcentagem);
                        relatorio_3=relatorio_3+cont_per+" - "+set_ocorrencias.get(x)+" com "+porcentagem_formatado+"% das Ocorrência(s)\n";
                        tot_ocorrencias.remove(x);
                        set_ocorrencias.remove(x);
                    }
                }

            }
            try {
                meu_relatorio.insertString(meu_relatorio.getLength(), relatorio_3 + "\n", highAlert);
                meu_relatorio.setParagraphAttributes(0,meu_relatorio.getLength(),highAlert, false);
            } catch (BadLocationException badLocationException) {
                txt_relatorio.setText(relatorio_3);
            }

            frame_relatorio.setTitle("Porcentagem de Ocorrências");
            frame_relatorio.setVisible(true);       
        } else if (relatorio==4) {
            frame_relatorio.setIconImage(icon_relatorio.getImage());
            ArrayList<Integer> sort_locais = new ArrayList<Integer>(tot_local);
            Collections.sort(sort_locais, Collections.reverseOrder());
            int cont_lo=0;
            for(int z = 0; z < sort_locais.size(); z++){
                for(int x = 0; x < tot_local.size(); x++){
                    if (sort_locais.get(z)==tot_local.get(x)) {
                        cont_lo++;
                        relatorio_4=relatorio_4+cont_lo+" - "+set_local.get(x)+" com "+sort_locais.get(z)+" Ocorrência(s)\n";
                        tot_local.remove(x);
                        set_local.remove(x);
                    }
                }
            }
            try {
                meu_relatorio.insertString(meu_relatorio.getLength(), relatorio_4 + "\n", highAlert);
                meu_relatorio.setParagraphAttributes(0,meu_relatorio.getLength(),highAlert, false);

            } catch (BadLocationException badLocationException) {
              txt_relatorio.setText(relatorio_4);
            }
            frame_relatorio.setTitle("Localidades e suas ocorrências");

            frame_relatorio.setVisible(true);       
        } else if (relatorio==5) {
            String texto_5="Site: http://javafree.uol.com.br/artigo/871500/Criando-uma-Janela-Swing.html\n\n"+
            "Site: http://www.javaprogressivo.net/2012/09/como-usar-arraylist-em-java.html\n\n"+
            "Site: http://stackoverflow.com/questions/17700530/blurry-text-after-setting-styleconstants-setfirstlineindent-as-negative-in-swing\n\n"+
            "Site: https://www.javatpoint.com/java-swing\n\n"+
            "Site: http://docs.oracle.com/javase/7/docs/api/javax/swing/JTextArea.html#setLineWrap(boolean)\n\n";
            try {
                meu_relatorio.insertString(meu_relatorio.getLength(), texto_5 + "\n", seminormal);
                meu_relatorio.setParagraphAttributes(0,meu_relatorio.getLength(),seminormal, false);

            } catch (BadLocationException badLocationException) {
              txt_relatorio.setText(texto_5);
            }
            frame_relatorio.setIconImage(icon_info.getImage());
            frame_relatorio.setTitle("Bibliografia");
            frame_relatorio.setVisible(true); 
        } else if (relatorio==6) {
            String texto_6="Aluno: Claudionor Júnior Nascimento\n\nDescrição do programa: O programa foi desenvolvido em consonância com o que foi estabelecido na PAS"+
            " da diciplina de Introdução a Programação do Curso de Análise e desenvolvimento de Sistemas "+
            "da Universidade Tiradentes.\n\nSobre o autor: Este autor que vos fala é um entusiasta Python, porém aceitou o desafio em "+
            "criar o presente programa na linguagem Java, utilizando como editor o Sublime Text e o Eclipse "+
            "para fazer o empacotamento final, espero que aproveitem o programa.\n\n";
            try {
                meu_relatorio.insertString(meu_relatorio.getLength(), texto_6 + "\n", seminormal);
                meu_relatorio.setParagraphAttributes(0,meu_relatorio.getLength(),seminormal, false);

            } catch (BadLocationException badLocationException) {
              txt_relatorio.setText(texto_6);
            }
            frame_relatorio.setIconImage(icon_info.getImage());
            frame_relatorio.setTitle("Sobre o Programa e o Autor");
            frame_relatorio.setVisible(true); 
        }
    }
    private void LerArquivo(){
        BufferedReader buffRead = null;
        String [] campos = new String[4];
        dados_da_tabela = new ArrayList<String[]>();
        conteudo_arquivo = new String();
        String cont_local=new String();

        try {
            buffRead = new BufferedReader(new FileReader("dados"));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        while (true) {
            String linha="";
            try {
                linha = buffRead.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (linha != null) {
                cont_local = cont_local+linha+"\n";
                campos=linha.split(";");
                dados_da_tabela.add(campos); 
            } else{
                break;
            }
        }
        try {
            buffRead.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        conteudo_arquivo=cont_local;

    }
    public static void GravarArquivo(String conteudo, Boolean manter) {
        BufferedWriter buffWrite = null;
        try {
            buffWrite = new BufferedWriter(new FileWriter("dados", manter));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            buffWrite.append(conteudo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            buffWrite.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void GerarInterface(){
        novos_envolvidos = new ArrayList<String>();
        setTitle("Registro de Ocorrências");
        ImageIcon icon = new ImageIcon("imagens/favicon.png");
        LerArquivo();
        JMenuBar barra_de_menus = new JMenuBar();
        JMenu menu = new JMenu("Relatórios");
        JMenu menu_sobre = new JMenu("Informações");
        menu.setMnemonic(KeyEvent.VK_R);
        menu_sobre.setMnemonic(KeyEvent.VK_S);
        menu.getAccessibleContext().setAccessibleDescription("Relatórios de ocorrências");
        menu_sobre.getAccessibleContext().setAccessibleDescription("Informações");
        barra_de_menus.add(menu);
        barra_de_menus.add(menu_sobre);
        JMenuItem menuItemOco = new JMenuItem("Ocorrências que mais se repetem", KeyEvent.VK_O);
        ActionListener al_menuItemOco = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Relatorios(1);
            }
        };
        menuItemOco.addActionListener(al_menuItemOco);

        JMenuItem menuItemEnv = new JMenuItem("Envolvidos por tipo de ocorrência", KeyEvent.VK_E);
        ActionListener al_menuItemEnv = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Relatorios(2);
            }
        };
        menuItemEnv.addActionListener(al_menuItemEnv);

        JMenuItem menuItemPer = new JMenuItem("Ocorrências em percentual", KeyEvent.VK_P);
        ActionListener al_menuItemPer = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Relatorios(3);
            }
        };
        menuItemPer.addActionListener(al_menuItemPer);

        JMenuItem menuItemLoc = new JMenuItem("Localidades e quantidade de ocorrências", KeyEvent.VK_L);
        ActionListener al_menuItemLoc = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Relatorios(4);
            }
        };
        menuItemLoc.addActionListener(al_menuItemLoc);


        menu.add(menuItemOco);
        menu.add(menuItemEnv);
        menu.add(menuItemPer);
        menu.add(menuItemLoc);

        JMenuItem bibliografia = new JMenuItem("Bibliografia", KeyEvent.VK_B);
        ActionListener al_bibliografia = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Relatorios(5);
            }
        };
        bibliografia.addActionListener(al_bibliografia);

        JMenuItem sobre = new JMenuItem("Sobre", KeyEvent.VK_S);
        ActionListener al_sobre = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Relatorios(6);
            }
        };
        sobre.addActionListener(al_sobre);

        menu_sobre.add(bibliografia);
        menu_sobre.add(sobre);


        setJMenuBar(barra_de_menus);
        String [] campos = {"Ocorrência", "Local", "Descrição", "Envolvidos"};
        Object [][] dados = new Object[dados_da_tabela.size()][4];
        lista_ocorrencias = new ArrayList<String>();
        lista_locais = new ArrayList<String>();
        JLabel lbl_ocorrencia = new JLabel("Ocorrência*");
        cbox_ocorrencia = new JComboBox<String>();
        cbox_ocorrencia.setEditable(true);
        JLabel lbl_descricao = new JLabel("Descrição de como a ocorrência aconteceu");
        cbox_local = new JComboBox<String>();
        cbox_local.setEditable(true);
        cbox_ocorrencia.addItem("");
        cbox_local.addItem("");
        tabela = new JTable();
        DefaultTableModel model = new DefaultTableModel(0, 0);
        model.setColumnIdentifiers(campos);
        tabela.setModel(model);
        for (int i = 0; i < dados_da_tabela.size(); i++) {
            for (int x = 0; x < 4; x++){
                if (x==0) {
                    if(lista_ocorrencias.contains(dados_da_tabela.get(i)[0])){    
                    } else {
                        lista_ocorrencias.add(dados_da_tabela.get(i)[0]);
                        cbox_ocorrencia.addItem(dados_da_tabela.get(i)[0]);
                    }
                }
                if (x==1) {
                    if(lista_locais.contains(dados_da_tabela.get(i)[1])){    
                    } else {
                        lista_locais.add(dados_da_tabela.get(i)[1]);
                        cbox_local.addItem(dados_da_tabela.get(i)[1]);
                    }
                }
                dados[i][x] = dados_da_tabela.get(i)[x];
            }
            String dado1=dados_da_tabela.get(i)[0];
            String dado2=dados_da_tabela.get(i)[1];
            String dado3=dados_da_tabela.get(i)[2];
            String dado4=dados_da_tabela.get(i)[3];
            model.addRow(new Object[] {dado1, dado2, dado3, dado4});
        }
        JScrollPane scrollPanel_tab = new JScrollPane(tabela);
        scrollPanel_tab.setMaximumSize(new Dimension(100,200));
        panel = new JPanel();
        JLabel lbl_titulo = new JLabel("FORMULÁRIO PARA REGISTRO DE PRECONCEITOS");
        lbl_titulo.setFont(new Font("Arial", Font.PLAIN, 18));
        lbl_titulo.setSize(20, 500);
        JLabel lbl_local = new JLabel("Local onde ocorreu*");
        JLabel lbl_tabela = new JLabel("Tabela de todas as ocorrências registradas");
        JTextArea txt_descricao = new JTextArea(10,40);
        txt_descricao.setLineWrap(true);
        Border border = BorderFactory.createLineBorder(new Color(0xc8c8c8), 1);
        JScrollPane scrollPanel = new JScrollPane(txt_descricao);
        lbl_envolvidos = new JLabel("Lista de envolvidos*");
        lbl_envolvidos.setForeground(new Color(0xc8c8c8));
        lbl_envolvidos.setOpaque(true);
        lbl_envolvidos.setBorder(border);
        JButton btn_envolvidos = new JButton("Adicionar Envolvidos*");

        ActionListener al_btn_envolvidos = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome_envolvido=JOptionPane.showInputDialog("Digite o nome do envolvido:");
                String nome_anterior = lbl_envolvidos.getText();
                if((nome_envolvido==null) || (nome_envolvido.isEmpty())){
                } 
                else {
                    if (nome_anterior=="Lista de envolvidos*"){
                        nome_anterior=nome_envolvido;
                    } 
                    else {
                        nome_anterior=nome_anterior+", "+nome_envolvido;
                    }
                    novos_envolvidos.add(nome_envolvido);
                    lbl_envolvidos.setText(nome_anterior);
                    lbl_envolvidos.setForeground(new Color(0x000000));  
                }
            }
        };
        btn_envolvidos.addActionListener(al_btn_envolvidos);
        JButton btn_salvar_form = new JButton("Registrar Ocorrência");
        ActionListener al_btn_salvar_form = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String combo1 = (String)cbox_ocorrencia.getSelectedItem();
                String combo2 = (String)cbox_local.getSelectedItem();
                String ocorrencia_atual= combo1;
                String local_atual= combo2;
                String descricao_atual= txt_descricao.getText();
                String envolvidos_atual= lbl_envolvidos.getText();
                if ((envolvidos_atual=="Lista de envolvidos") || (combo1.isEmpty()) || (combo2.isEmpty())){
                    if (combo1.isEmpty()){
                        JOptionPane.showMessageDialog(null, "<html>O campo <i>ocorrências</i> não pode ficar vazio<br>Os campos com asterisco(*) são obrigatórios.</html>");
                    } else if (combo2.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "<html>O campo <i>local</i> não pode ficar vazio</html>");
                    } else if (envolvidos_atual=="Lista de envolvidos*") {
                        JOptionPane.showMessageDialog(null, "<html>O campo <i>envolvidos</i> não pode ficar vazio<br>Os campos com asterisco(*) são obrigatórios.</html>");
                    }
                } else {
                    lbl_envolvidos.setForeground(new Color(0xc8c8c8));
                        lbl_envolvidos.setText("Lista de envolvidos*");
                        lbl_envolvidos.setForeground(new Color(0xc8c8c8));
                        txt_descricao.setText("");
                        if(lista_ocorrencias.contains(ocorrencia_atual)){

                        }else {
                            cbox_ocorrencia.insertItemAt(ocorrencia_atual, 1);
                        }
                        if(lista_locais.contains(local_atual)){

                        }else {         
                            cbox_local.insertItemAt(local_atual, 1);
                        }
                        cbox_ocorrencia.setSelectedIndex(-1);
                        cbox_local.setSelectedIndex(-1);
                        
                        model.addRow(new Object[]{ocorrencia_atual, local_atual, descricao_atual,envolvidos_atual});
                        String para_arquivo= String.format("%s;%s;%s;%s\n", ocorrencia_atual, local_atual, descricao_atual,envolvidos_atual);
                        conteudo_arquivo=conteudo_arquivo+para_arquivo;
                        GravarArquivo(conteudo_arquivo.trim(), false);
                        JOptionPane.showMessageDialog(null, "Ocorrência Adicionada!");      
                }
            }
        };
        btn_salvar_form.addActionListener(al_btn_salvar_form);

        btn_salvar_lista = new JButton("Salvar Alterações da tabela");
        TableModelListener tl_tabela = new TableModelListener(){
            public void tableChanged(TableModelEvent e) {
                btn_salvar_lista.setEnabled(true);
            };
        };
        model.addTableModelListener(tl_tabela);

        btn_salvar_lista.setEnabled(false);
        ActionListener al_btn_salvar_lista = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String dados_da_tabela_modificada = "";
                for (int count = 0; count < model.getRowCount(); count++){
                  dados_da_tabela_modificada=dados_da_tabela_modificada+String.format("%s;%s;%s;%s\n", model.getValueAt(count, 0).toString(), model.getValueAt(count, 1).toString(), model.getValueAt(count, 2).toString(), model.getValueAt(count, 3).toString());
                }
                GravarArquivo(dados_da_tabela_modificada.trim(), false);
                JOptionPane.showMessageDialog(null, "Edição da Tabela Salva!");
                btn_salvar_lista.setEnabled(false);
            }       
        };
        btn_salvar_lista.addActionListener(al_btn_salvar_lista);

        setSize(1000, 600);
        setMinimumSize(new Dimension(800, 600));
        GridBagConstraints cons = new GridBagConstraints(); 
        GridBagLayout layout = new GridBagLayout();
        scrollPanel.setBounds(5, 5, 100, 100);
        scrollPanel_tab.setBounds(5, 5, 100, 100);
        cons.insets = new Insets(10,10,10,10);
        cons.weightx = 1;
        cons.weighty = 1;
        panel.setLayout(layout);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setIconImage(icon.getImage());
        add(panel);
        cons.gridy = 0;
        cons.gridx = 0;
        cons.gridwidth = 6; 
        cons.gridheight = 1;

        panel.add(lbl_titulo, cons);
        cons.insets = new Insets(2,20,2,20);
        cons.gridy = 1;
        cons.gridx = 0;
        cons.gridwidth = 2; 
        cons.gridheight = 1;
        cons.insets = new Insets(2,20,2,0);
        panel.add(lbl_ocorrencia, cons);

        cons.weightx = 700;
        cons.gridy = 1;
        cons.gridx = 2;
        cons.gridwidth = 4;
        cons.gridheight = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.insets = new Insets(2,0,2,20);
        panel.add(cbox_ocorrencia, cons);
        cons.weightx = 1;
        cons.ipadx = 0; 
        cons.gridy = 2;
        cons.gridx = 0;
        cons.gridwidth = 2;
        cons.gridheight = 1;
        cons.fill = GridBagConstraints.NONE;
        cons.insets = new Insets(2,20,2,0);
        panel.add(lbl_local, cons);

        cons.gridy = 2;
        cons.gridx = 2;
        cons.gridwidth = 4;
        cons.gridheight = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.insets = new Insets(2,0,2,20);
        panel.add(cbox_local, cons);
        cons.ipadx = 0; 
        cons.gridy = 3;
        cons.gridx = 0;
        cons.gridwidth = 6;
        cons.fill = GridBagConstraints.NONE;
        cons.anchor = GridBagConstraints.SOUTHWEST;
        cons.insets = new Insets(2,20,2,20);
        panel.add(lbl_descricao, cons);
        cons.weighty = 200;
        cons.gridy = 4;
        cons.gridx = 0;
        cons.gridwidth = 6;
        cons.gridheight = 2; 
        cons.fill = GridBagConstraints.BOTH;
        cons.anchor = GridBagConstraints.CENTER;
        panel.add(scrollPanel, cons);

        cons.fill = GridBagConstraints.NONE;
        cons.weighty = 0;
        cons.gridy = 6;
        cons.gridx = 0;
        cons.gridwidth = 2;
        cons.gridheight = 1; 

        cons.insets = new Insets(2,20,2,0);
        cons.fill = GridBagConstraints.HORIZONTAL;      
        panel.add(btn_envolvidos, cons);
        cons.weightx = 700;
        cons.gridy = 6;
        cons.gridx = 2;
        cons.gridwidth = 4;
        cons.gridheight = 1;
        cons.fill = GridBagConstraints.BOTH;
        cons.insets = new Insets(2,0,2,20);
        panel.add(lbl_envolvidos,cons);
        
        cons.weightx = 1;
        cons.gridy = 7;
        cons.gridx = 0;
        cons.gridwidth = 6;
        cons.fill = GridBagConstraints.NONE;
        cons.insets = new Insets(20,20,0,20);
        panel.add(btn_salvar_form, cons);

        cons.gridy = 8;
        cons.gridx = 0;
        cons.gridwidth = 6; 
        cons.gridheight = 1; 

        cons.fill = GridBagConstraints.BOTH;
        cons.insets = new Insets(20,0,2,0);
        panel.add(new JSeparator(), cons);
        cons.insets = new Insets(2,20,2,20); 
        cons.gridy = 9;
        cons.gridx = 0;
        cons.gridwidth = 6;
        cons.fill = GridBagConstraints.NONE;
        cons.anchor = GridBagConstraints.WEST;
        cons.insets = new Insets(2,20,2,20);
        panel.add(lbl_tabela, cons);
        cons.weighty = 500;
        cons.gridy =10;
        cons.gridx = 0;
        cons.gridwidth = 6; 
        cons.gridheight = 1;
        cons.fill = GridBagConstraints.BOTH;        
        cons.anchor = GridBagConstraints.CENTER;
        panel.add(scrollPanel_tab, cons); 
        cons.weightx = 1;
        cons.weighty = 1;
        cons.gridy = 11;
        cons.gridx = 0;
        cons.gridwidth = 6;
        cons.fill = GridBagConstraints.NONE;
        cons.insets = new Insets(0,20,20,20);
        panel.add(btn_salvar_lista, cons);
    }
    public static void main(String[] args) {
        Pas frame = new Pas();
        frame.GerarInterface();
        frame.setVisible(true);
    }
}
