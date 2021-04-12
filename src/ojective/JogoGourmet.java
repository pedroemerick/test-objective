package ojective;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class JogoGourmet {
	
	private List<Prato> pratos;
	
	public JogoGourmet() {
		super();
		
		pratos = new ArrayList<>();
		
		UIManager.put("OptionPane.cancelButtonText", "Cancelar");
		UIManager.put("OptionPane.noButtonText", "Não");
		UIManager.put("OptionPane.yesButtonText", "Sim");
	}

	public void play() {
		
		Locale locale = new Locale("pt","BR");
		JOptionPane.setDefaultLocale(locale);
		
		JFrame frame = new JFrame();
		frame.setTitle("Jogo Gourmet");
		frame.setSize(285, 130);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		
		JLabel label = new JLabel("Pense em um prato que gosta");
		c.gridy = 0;
		panel.add(label, c);
		
		JButton button = new JButton("OK");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				perguntaInicialTipo();
			}
		});
		
		c.gridy = 1;
		c.insets = new Insets(10, 0, 0, 0);
		panel.add(button, c);
		
		frame.add(panel);
		frame.setVisible(true);
	}
	
	private void perguntaInicialTipo() {

		int resposta = JOptionPane.showConfirmDialog(null, "O prato que você pensou é massa?", "Confirm", JOptionPane.YES_NO_OPTION);
	    if (resposta == JOptionPane.YES_OPTION) {
	        List<Prato> massas = getPratosPorTipoSemPai(TipoPrato.MASSA);
	        perguntas(massas, TipoPrato.MASSA);
	    } else {
	    	List<Prato> massas = getPratosPorTipoSemPai(TipoPrato.OUTRO);
	        perguntas(massas, TipoPrato.OUTRO);
	    }
	}
	
	private void perguntaFinal(Prato prato) {
		int yy = perguntaPrato(prato.getDescricao());
		if (yy == JOptionPane.YES_OPTION) {
			acerteiPrato();
		} else {
			perguntaAddPrato(prato);
		}
	}
	
	private void perguntas(List<Prato> pratosTipo, TipoPrato tipo) {
		for(int ii = 0; ii < pratosTipo.size(); ii++) {
			Prato prato = pratosTipo.get(ii);
			
        	int xx = perguntaPrato(prato.getQualidade());
        	if (xx == JOptionPane.YES_OPTION) {
        		List<Prato> pratosFilhos = getPratosPorPratoPaiId(getPratosPorTipo(tipo), prato.getId());
        		
        		if (pratosFilhos.size() == 0) {
        			perguntaFinal(prato);
        			return;
        		} else {
        			perguntas(pratosFilhos, tipo);
        			return;
        		}
        	} else {
        		if (ii == pratosTipo.size()-1) {
        			perguntaFinal(getPratoPorId(prato.getPratoPaiId(), tipo));
        			return;
        		}
        	}
        }
		
		String descricao;
		if (tipo.equals(TipoPrato.MASSA)) {
			descricao = "Lasanha";
		} else {
			descricao = "Bolo de Chocolate";
		}
		
		int xx = perguntaPrato(descricao);
    	if (xx == JOptionPane.YES_OPTION) {
    		acerteiPrato();
    	} else {
    		perguntaAddPrato(new Prato(null, tipo, descricao, null, null));
    	}
	}
	
	private int perguntaPrato(String descricao) {
		return JOptionPane.showConfirmDialog(null, "O prato que você pensou é " + descricao + "?", "Confirm", JOptionPane.YES_NO_OPTION);
	}
	
	private void acerteiPrato() {
		JOptionPane.showMessageDialog(null, "Acertei de novo!", "Jogo Gourmet", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void perguntaAddPrato(Prato prato) {
		String descricao = JOptionPane.showInputDialog(null, "Qual prato você pensou?", "Desisto", JOptionPane.QUESTION_MESSAGE);
		String qualidade = JOptionPane.showInputDialog(null, descricao + " é mais _______ mas " + prato.getDescricao() + " não.", "Desisto", JOptionPane.QUESTION_MESSAGE);
		addPrato(prato.getTipo(), descricao, qualidade, prato.getId());
	}
	
	private void addPrato(TipoPrato tipo, String descricao, String qualidade, Long pratoPaiId) {
		long id = this.pratos.size() + 1;
		this.pratos.add(new Prato(id, tipo, descricao, qualidade, pratoPaiId));
	}
	
	private List<Prato> getPratosPorTipo(TipoPrato tipo) {
		return this.pratos.stream()
				  .filter(prato -> prato.getTipo().equals(tipo))
				  .collect(Collectors.toList());
	}
	
	private List<Prato> getPratosPorTipoSemPai(TipoPrato tipo) {
		return this.pratos.stream()
				  .filter(prato -> prato.getTipo().equals(tipo) && Objects.isNull(prato.getPratoPaiId()))
				  .collect(Collectors.toList());
	}
	
	private List<Prato> getPratosPorPratoPaiId(List<Prato> pratos, Long id) {
		return pratos.stream()
				  .filter(prato -> !Objects.isNull(prato.getPratoPaiId()) && prato.getPratoPaiId().equals(id))
				  .collect(Collectors.toList());
	}
	
	private Prato getPratoPorId(Long id, TipoPrato tipo) {
		return pratos.stream()
				  .filter(prato -> !Objects.isNull(prato.getId()) && prato.getId().equals(id))
				  .findAny()
				  .orElse(tipo.equals(TipoPrato.MASSA) ? new Prato(null, tipo, "Lasanha", null, null) : new Prato(null, tipo, "Bolo de Chocolate", null, null));
	}
}
