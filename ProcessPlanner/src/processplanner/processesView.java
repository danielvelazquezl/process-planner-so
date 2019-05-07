package processplanner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author daniel
 */
public class processesView extends javax.swing.JFrame implements ActionListener {

    PlanificadorCPU cpu;

    Timer temp;
    int fps = 1;
    boolean pause = true;

    /**
     * Creates new form processesView
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public processesView() {
        initComponents();

        int delay = (fps > 0) ? (1000 / fps) : 100;
        temp = new Timer(delay, this);
        temp.setCoalesce(false);
        temp.setInitialDelay(0);

        cpu = new PlanificadorCPU("processes.txt");
        cpu.setFps(delay);

        this.run.addActionListener(this);
        this.stop.addActionListener(this);
        this.comboBoxAlgorithms.addActionListener(this);
        this.restart.addActionListener(this);
        this.addProcess.addActionListener(this);

        updateUiStatus();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        processName = new javax.swing.JTextField();
        burstAmount = new javax.swing.JTextField();
        addProcess = new javax.swing.JButton();
        run = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        comboBoxAlgorithms = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        messages = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        cpuTime = new javax.swing.JLabel();
        stop = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        restart = new javax.swing.JButton();
        pID = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Process Manager");
        setPreferredSize(new java.awt.Dimension(804, 489));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        processName.setToolTipText("Nombre de proceso");
        getContentPane().add(processName, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 124, -1));

        burstAmount.setToolTipText("Cantidad de rafagas");
        getContentPane().add(burstAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 110, 140, -1));

        addProcess.setText("Agregar");
        getContentPane().add(addProcess, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 110, 100, -1));

        run.setText("Iniciar");
        run.setToolTipText("");
        getContentPane().add(run, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, 70, -1));

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Nombre de proceso");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, -1, -1));

        jLabel2.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        jLabel2.setText("Cantidad de ráfagas");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 90, -1, -1));

        comboBoxAlgorithms.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FCFS", "SJF", "RR", "Colas multinivel" }));
        getContentPane().add(comboBoxAlgorithms, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 34, 140, -1));

        jLabel3.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel3.setText("Algoritmo a utilizar");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 12, -1, -1));

        messages.setEditable(false);
        messages.setColumns(20);
        messages.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        messages.setRows(5);
        jScrollPane3.setViewportView(messages);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 150, 395, 230));

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Proceso", "Ráfagas", "T. Llegada"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(table);

        getContentPane().add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 380, 230));

        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel4.setText("Tiempo de CPU:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 120, -1, -1));

        cpuTime.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        cpuTime.setText("0");
        getContentPane().add(cpuTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 120, -1, -1));

        stop.setText("Parar");
        stop.setToolTipText("");
        getContentPane().add(stop, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 30, 70, -1));

        jLabel5.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Nuevo proceso");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 270, -1));

        restart.setText("Reiniciar");
        restart.setToolTipText("");
        getContentPane().add(restart, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 30, 90, -1));

        pID.setToolTipText("PID");
        getContentPane().add(pID, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 60, -1));

        jLabel6.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        jLabel6.setText("PID");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(processesView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(processesView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(processesView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(processesView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new processesView().setVisible(true);
            }
        });
    }

    // <editor-fold defaultstate="collapsed" desc="Variables">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addProcess;
    private javax.swing.JTextField burstAmount;
    private javax.swing.JComboBox<String> comboBoxAlgorithms;
    private javax.swing.JLabel cpuTime;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea messages;
    private javax.swing.JTextField pID;
    private javax.swing.JTextField processName;
    private javax.swing.JButton restart;
    private javax.swing.JButton run;
    private javax.swing.JButton stop;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
// </editor-fold>  
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        //ejecutar
        if (ae.getSource() == run) {
            cpu.setPaused(false);
            pause = false;
            starSimulation();
        } //parar la ejecucion
        else if (ae.getSource() == stop) {
            pause = true;
            stopSimulation();
            cpu.setPaused(true);
        } else if (ae.getSource() == temp) {
            if (cpu.nextCycle()) {
                updateUiStatus();
            } else {
                stopSimulation();
            }
            repaint();
        } // Algoritmos a escojer
        else if (ae.getSource() == comboBoxAlgorithms) {
            if ("FCFS".equals(comboBoxAlgorithms.getSelectedItem().toString())) {
                cpu.setAlgorithm(PlanificadorCPU.FCFS);
            }
            if ("SJF".equals(comboBoxAlgorithms.getSelectedItem().toString())) {
                cpu.setAlgorithm(PlanificadorCPU.SJF);
            }
            if ("RR".equals(comboBoxAlgorithms.getSelectedItem().toString())) {
                cpu.setAlgorithm(PlanificadorCPU.ROUNDROBIN);
            }
        } //reiniciar procesos
        else if (ae.getSource() == restart) {
            cpu.restart();
            updateUiStatus();
            messages.setText("");
            repaint();
        }//Agregar proceso
        else if(ae.getSource() == addProcess){
            cpu.addProcess(new PCB(Integer.parseInt(pID.getText()), processName.getText(), 
                    Integer.parseInt(burstAmount.getText()), (int )cpu.getCurrentTime())
            );
        }

    }

    private synchronized void starSimulation() {
        if (pause) {

        } else {
            if (!temp.isRunning()) {
                temp.start();
            }
        }
    }

    private synchronized void stopSimulation() {
        if (temp.isRunning()) {
            temp.stop();
        }
    }

    private void updateUiStatus() { //reloj
        cpuTime.setText(Integer.toString((int) cpu.getCurrentTime()));
        if (cpu.getActiveProcess() != null) {
            updateMessages();
            updateTable();
        }
    }
    
    private void updateMessages() {
        messages.append("Tiempo: " + cpu.getCurrentTime() + " => " + 
                cpu.getActiveProcess().getpName() + " en ejecucion\n");

    }
    
    private void updateTable() {
        
    }

}
