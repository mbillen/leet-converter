package leet;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.lang3.StringUtils;

public class Converter implements Runnable {
    JFrame frame;
    JTextArea raw, leet;
    boolean isSettingPreset, isApplyingPreset;
    JComboBox<Preset> presets;
    Map<Alphabet, JComboBox<String>> comboRegistry = new HashMap<Alphabet, JComboBox<String>>();

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        SwingUtilities.invokeLater(new Converter());
    }
    
    @Override
    public void run() {
        frame = new JFrame("L33T Converter 0.1.1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(createWestPanel(), BorderLayout.WEST);
        frame.add(createCenterPanel(), BorderLayout.CENTER);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
    
    private JPanel createWestPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.PAGE_START;
        
        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(3, 5, 10, 1);
        c.gridwidth = 6;
        
        panel.add(createPresetCombo(), c);
        c.gridy++;
        
        c.gridwidth = 1;
        c.insets = new Insets(3, 5, 3, 1);
        for(Alphabet a : Alphabet.values()) {
            panel.add(new JLabel(a.name()), c);
            c.gridx++;
            
            panel.add(createCombo(a), c);
            if(c.gridx%6 == 0) {
                c.gridy++;
                c.gridx = 1;
            }
            else {
                c.gridx++;
            }
        }
        
        c.gridx = 3;
        c.gridy++;
        c.insets = new Insets(10, 5, 10, 1);
        c.gridwidth = 2;
        
        panel.add(new JButton(getPresetAction), c);
        
        return panel;
    }
    
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        
        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(5, 10, 5, 5);
        c.gridwidth = 2;
        raw = new JTextArea(10, 40);
        panel.add(new JScrollPane(raw), c);
        
        c.gridy++;
        c.gridwidth = 1;
        panel.add(new JButton(toLeetAction), c);
        c.gridx++;
        panel.add(new JButton(fromLeetAction), c);
        
        c.gridx = 1;
        c.gridy++;
        c.gridwidth = 2;
        leet = new JTextArea(10, 40);
        panel.add(new JScrollPane(leet), c);
        
        return panel;
    }
    
    private JComboBox<String> createCombo(Alphabet a) {
        JComboBox<String> cb = new JComboBox<>();
        cb.setActionCommand(a.name());
        cb.addActionListener(l -> { 
            if(!isApplyingPreset) { 
                isSettingPreset = true; 
                presets.setSelectedIndex(0); 
                isSettingPreset = false; 
            }});
        for(String s : a.translations)
            cb.addItem(s);
        
        comboRegistry.put(a, cb);
        return cb;
    }
    
    private JComboBox<Preset> createPresetCombo() {
        presets = new JComboBox<Preset>();
        presets.addItem(new Preset("Custom"));

        try {
            Properties preset_props = new Properties();
            preset_props.load(Files.newInputStream(Paths.get("leetconverter.properties")));
            for(String name : preset_props.stringPropertyNames())
                presets.addItem(new Preset(name).mapFromString(preset_props.getProperty(name)));
        }
        catch(IOException ex) {
        }
        
        presets.addActionListener(l -> { 
            if(!isSettingPreset) {
                isApplyingPreset = true;
                Map<Alphabet, Integer> m = ((Preset)presets.getSelectedItem()).indexMap;
                comboRegistry.entrySet().parallelStream().forEach(e -> {
                    if(m.containsKey(e.getKey()))
                        e.getValue().setSelectedIndex(m.get(e.getKey()));
                    else
                        e.getValue().setSelectedIndex(0);
                });
                isApplyingPreset = false;
            }});
        
        return presets;
    }
    
    private Action toLeetAction = new AbstractAction("to L33T") {
        private static final long serialVersionUID = 1146211209029719911L;

        @Override
        public void actionPerformed(ActionEvent e) {
            
            try {
                List<Entry<Alphabet, JComboBox<String>>> l = comboRegistry.entrySet().stream().collect(Collectors.toList());
                String[] searchList = l.stream().map(entry -> (String)entry.getKey().name()).toArray(String[]::new);
                String[] replacementList = l.stream().map(entry -> (String)entry.getValue().getSelectedItem()).toArray(String[]::new);
                leet.setText(StringUtils.replaceEach(raw.getText().toUpperCase(), searchList, replacementList));
            } 
            catch(Exception ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    };
    
    private Action fromLeetAction = new AbstractAction("from L33T") {
        private static final long serialVersionUID = 4619730361010277729L;

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                List<Entry<Alphabet, JComboBox<String>>> l = comboRegistry.entrySet().stream().collect(Collectors.toList());
                String[] searchList = l.stream().map(entry -> (String)entry.getValue().getSelectedItem()).toArray(String[]::new);
                String[] replacementList = l.stream().map(entry -> (String)entry.getKey().name()).toArray(String[]::new);
                raw.setText(StringUtils.replaceEach(leet.getText().toUpperCase(), searchList, replacementList));
            } 
            catch(Exception ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    };
    
    private Action getPresetAction = new AbstractAction("Get Preset") {
        private static final long serialVersionUID = -6822610601540723052L;

        @Override
        public void actionPerformed(ActionEvent e) {
            String presetString = comboRegistry.entrySet().stream()
                .filter(entry -> (entry.getValue().getSelectedIndex() > 0))
                .map(entry -> String.join(",", entry.getKey().name(), String.valueOf(entry.getValue().getSelectedIndex())))
                .collect(Collectors.joining(","));
            
            JTextField tf = new JTextField(40);
            tf.setEditable(false);
            tf.setText(presetString);
            JOptionPane.showMessageDialog(frame, tf, "Preset", JOptionPane.INFORMATION_MESSAGE);
        }
    };
}
