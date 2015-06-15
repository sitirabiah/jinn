package org.salemelrahal.jinn.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Layer {
	protected List<Neuron> neurons;
	protected List<Link> links = Collections.emptyList();
	
	protected Layer() {
		
	}

	public Layer(int size) {
		neurons = new ArrayList<Neuron>(size);
		for (int i = 0 ; i < size ; i++){
			neurons.add(new Neuron());
		}
	}
	
	public void learn() {
		for (Neuron neuron: neurons) {
			neuron.learn();
		}
		
		for (Link link : links) {
			link.learn();
		}
	}
	
	public void backpropagate() {
		for (Link link : links) {
			link.backPropagate();
		}
	}
	
	public void updateRunningError(BigDecimal learningRateFactor) {
		for (Neuron neuron: neurons) {
			neuron.updateRunningError(learningRateFactor);
		}
		
		for (Link link : links) {
			link.updateRunningError(learningRateFactor);
		}
	}
	
	public void fireLinks() {
		for (Link link : links) {
			link.fire();
		}
	}
	
	public void before(Layer nextLayer) {
		links = new ArrayList<Link>(neurons.size() * nextLayer.neurons.size());
		for (Neuron from : neurons) {
			for (Neuron to : nextLayer.neurons) {
				links.add(new Link(from, to));
			}
		}
	}
	
	public void resetErrors() {
		for (Neuron neuron : neurons) {
			neuron.setError(BigDecimal.ZERO);
		}
	}
	
	public void resetNetInput() {
		for (Neuron neuron : neurons) {
			neuron.setNetInput(BigDecimal.ZERO);
		}
	}
	
	public List<Link> getLinks() {
		return links;
	}
	
	public Iterator<Neuron> neuronIterator() {
		return neurons.iterator();
	}
	
	public List<Neuron> getNeurons() {
		return neurons;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Layer [");
		for (Neuron neuron: neurons) {
			sb.append(neuron.getActivation());
			sb.append("(");
			sb.append(neuron.getNetInput());
			sb.append("),");
		}
		return sb.toString();
	}

	public BigDecimal hashed() {
		BigDecimal count = BigDecimal.ZERO;
		for (Link link : links) {
			count = count.add(link.hashed());
		}
		for (Neuron neuron : neurons) {
			count = count.add(neuron.hashed());
		}
		return count;
	}
}
