package chart;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarCharts
{
	public static JPanel createCategoryBar(String chartName,String xName,String yName,List<Double> result) {
		CategoryDataset dataSet = createDataset(result);
		JFreeChart localJFreeChart = createChart(chartName,xName,yName,dataSet);
		return new ChartPanel(localJFreeChart);
	}

	private static JFreeChart createChart(String chartName,String xName,String yName,CategoryDataset paramCategoryDataset)
	{
		JFreeChart localJFreeChart = ChartFactory.createBarChart(chartName,
    		xName,
    		yName,
    		paramCategoryDataset,
    		PlotOrientation.VERTICAL,
    		true,
    		true,
    		false);
		CategoryPlot localCategoryPlot = (CategoryPlot)localJFreeChart.getPlot();
		localCategoryPlot.setBackgroundPaint(Color.WHITE);									//����ɫ
		localCategoryPlot.getRangeAxis().setTickLabelFont(new Font("����", Font.PLAIN, 10)); //��������������
		localCategoryPlot.getRangeAxis().setLabelFont(new Font("����", Font.BOLD, 15)); 		//�ݱ�����������
		localCategoryPlot.getDomainAxis().setTickLabelFont(new Font("������", 1, 10)); 		//��������������
		localCategoryPlot.getDomainAxis().setLabelFont(new Font("������", 1, 15)); 			//�������������
		localCategoryPlot.setRangeGridlinesVisible(true);									//����������
		localCategoryPlot.setRangeGridlinePaint(Color.gray);
		localJFreeChart.getLegend().setItemFont(new Font("����", Font.ITALIC | Font.BOLD, 15)); 	//����Ŀ�����������
		localJFreeChart.getLegend().setVisible(false);
		localJFreeChart.getTitle().setFont(new Font("����", Font.BOLD, 17));					//�ܱ�����������

		NumberAxis localNumberAxis = (NumberAxis)localCategoryPlot.getRangeAxis();
		localNumberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		localNumberAxis.setUpperMargin(0.15D);
		
		// ��ͼ�ĳ�����
		BarRenderer renderer = (BarRenderer) localCategoryPlot.getRenderer();
		renderer.setIncludeBaseInRange(true); 	// ��ʾÿ��������ֵ�����޸ĸ���ֵ����������
		renderer.setMaximumBarWidth(0.05);		// ���������
		renderer.setItemMargin(0.05); 			// �������Ӽ��Ϊ����10%
		renderer.setBaseOutlinePaint(Color.BLACK); 	// �������ӱ߿���ɫ
		renderer.setDrawBarOutline(true);		 	// �������ӱ߿�ɼ�
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true,true);
   
		CategoryAxis localCategoryAxis = localCategoryPlot.getDomainAxis();
		localCategoryAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
		
		
		return localJFreeChart;
	}
	
	private static CategoryDataset createDataset(List<Double> result)
	{
		DefaultCategoryDataset localDefaultCategoryDataset = new DefaultCategoryDataset();
		for(int i = 0; i < result.size(); i++){
			Double value = result.get(i);
			localDefaultCategoryDataset.addValue(value,"", (Integer)i);
		}
		return localDefaultCategoryDataset;
	}
}