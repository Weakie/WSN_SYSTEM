package chart;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class LineCharts{
	

	// ������ʾͼ������
	public static JPanel createXYLine(double[] fitness,double[] coverage,double[] distance) {
		JFreeChart jfreechart = createChart(createDataset(fitness,coverage,distance));
		return new ChartPanel(jfreechart);
	}
	// ����ͼ��������JFreeChart
	public static JFreeChart createChart(XYDataset linedataset) {
		// ����ͼ�����
		JFreeChart chart = ChartFactory.createXYLineChart("�㷨��������", // chart title
				"��������", // domain axis label
				"ֵ(%)", // range axis label
				linedataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips
				false // urls
				);
		XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.WHITE);									//����ɫ
		plot.getRangeAxis().setTickLabelFont(new Font("����", Font.PLAIN, 12)); //��������������
		plot.getRangeAxis().setLabelFont(new Font("����", Font.BOLD, 10)); //�ݱ������������
		plot.getDomainAxis().setTickLabelFont(new Font("������", 1, 15)); //��������������
		plot.getDomainAxis().setLabelFont(new Font("������", 1, 12)); //�������������
		chart.getLegend().setItemFont(new Font("����", Font.ITALIC, 15)); //��������Ŀ�����������
		chart.getTitle().setFont(new Font("����", Font.BOLD, 12));// �ܱ�����������
		
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setAutoRangeIncludesZero(true);
		rangeAxis.setUpperMargin(0.20);
		rangeAxis.setLabelAngle(Math.PI / 2.0);

		return chart;
	}

	// ��������
	public static XYSeriesCollection createDataset(double[] fitness,double[] coverage,double[] distance) {
		XYSeriesCollection dataset = new XYSeriesCollection();
		// ����������
		XYSeries seriesFitness = new XYSeries("��Ӧ��(���w��+200/�ƶ�����)");
		XYSeries seriesCoverage = new XYSeries("���w��");
		XYSeries seriesDistance = new XYSeries("20000/�ƶ�����");
		
		// �����ߵ�ֵ
		for (int i = 0; i < coverage.length; i++) {
			fitness[i]=(fitness[i]>1.5?1.5:fitness[i]);
			seriesFitness.add(i, fitness[i] * 100);
		}
		for (int i = 0; i < coverage.length; i++) {
			coverage[i]=(coverage[i]>1.5?1.5:coverage[i]);
			seriesCoverage.add(i, coverage[i] * 100);
		}
		for (int i = 0; i < coverage.length; i++) {
			distance[i]=(100/distance[i]>1.5?100/1.5:distance[i]);
			seriesDistance.add(i, 200 / distance[i] * 100);
		}
		// �ӵ�DataSet��
		dataset.addSeries(seriesFitness);
		dataset.addSeries(seriesCoverage);
		dataset.addSeries(seriesDistance);
		
		return dataset;
	}
	

}