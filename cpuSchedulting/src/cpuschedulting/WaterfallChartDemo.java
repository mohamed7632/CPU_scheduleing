//package cpuschedulting;
//
//import java.awt.Color;
//import java.text.DecimalFormat;
//
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.axis.NumberTickUnit;
//import org.jfree.chart.axis.TickUnits;
//import org.jfree.chart.axis.ValueAxis;
//import org.jfree.chart.plot.CategoryPlot;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.chart.renderer.category.BarRenderer;
//import org.jfree.data.category.CategoryDataset;
//import org.jfree.data.category.DefaultCategoryDataset;
//import org.jfree.ui.ApplicationFrame;
//import org.jfree.ui.RefineryUtilities;
//
///**
// * A sample waterfall chart.
// */
//public class WaterfallChartDemo extends ApplicationFrame {
//
//    /**
//     * Creates a new WaterFall Chart demo.
//     * 
//     * @param title  the frame title.
//     */
//    public WaterfallChartDemo(final String title) {
//
//        super(title);
//        
//        final CategoryDataset dataset = createDataset();
//        final JFreeChart chart = createChart(dataset);
//        final ChartPanel chartPanel = new ChartPanel(chart);
//        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
//        chartPanel.setEnforceFileExtensions(false);
//        setContentPane(chartPanel);
//       
//    }
//
//    /**
//     * Creates a sample dataset for the demo.
//     * 
//     * @return A sample dataset.
//     */
//    private CategoryDataset createDataset() {
//        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        dataset.addValue(15.76, "Product 1", "Labour");
//        dataset.addValue(8.66, "Product 1", "Administration");
//        dataset.addValue(4.71, "Product 1", "Marketing");
//        dataset.addValue(3.51, "Product 1", "Distribution");
//        dataset.addValue(32.64, "Product 1", "Total Expense");
//        return dataset;
//    }
//    
//    // ****************************************************************************
//    // * JFREECHART DEVELOPER GUIDE                                               *
//    // * The JFreeChart Developer Guide, written by David Gilbert, is available   *
//    // * to purchase from Object Refinery Limited:                                *
//    // *                                                                          *
//    // * http://www.object-refinery.com/jfreechart/guide.html                     *
//    // *                                                                          *
//    // * Sales are used to provide funding for the JFreeChart project - please    * 
//    // * support us so that we can continue developing free software.             *
//    // ****************************************************************************
//    
//    /**
//     * Returns the chart.
//     * 
//     * @param dataset  the dataset.
//     *
//     * @return The chart.
//     */
//    private JFreeChart createChart(final CategoryDataset dataset) {
//        
//        final JFreeChart chart = ChartFactory.createWaterfallChart(
//            "chart",
//            "process",
//            "time",
//            dataset,
//            PlotOrientation.VERTICAL,
//            true,
//            true,
//            false
//        );
//        chart.setBackgroundPaint(Color.white);
//
//        final CategoryPlot plot = chart.getCategoryPlot();
//        plot.setBackgroundPaint(Color.lightGray);
//        plot.setRangeGridlinePaint(Color.red);
//        plot.setRangeGridlinesVisible(true);
////        plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
//        
//        final ValueAxis rangeAxis = plot.getRangeAxis();
//        
//        // create a custom tick unit collection...
//        final DecimalFormat formatter = new DecimalFormat("##,###");
//        formatter.setNegativePrefix("(");
//        formatter.setNegativeSuffix(")");
//        final TickUnits standardUnits = new TickUnits();
//        standardUnits.add(new NumberTickUnit(5, formatter));
//        standardUnits.add(new NumberTickUnit(10, formatter));
//        standardUnits.add(new NumberTickUnit(20, formatter));
//        standardUnits.add(new NumberTickUnit(50, formatter));
//        standardUnits.add(new NumberTickUnit(100, formatter));
//        standardUnits.add(new NumberTickUnit(200, formatter));
//        standardUnits.add(new NumberTickUnit(500, formatter));
//        standardUnits.add(new NumberTickUnit(1000, formatter));
//        standardUnits.add(new NumberTickUnit(2000, formatter));
//        standardUnits.add(new NumberTickUnit(5000, formatter));
//        rangeAxis.setStandardTickUnits(standardUnits);
//
//        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
//        renderer.setDrawBarOutline(false);
//
//        final DecimalFormat labelFormatter = new DecimalFormat("$##,###.00");
//        labelFormatter.setNegativePrefix("(");
//        labelFormatter.setNegativeSuffix(")");
////        renderer.setLabelGenerator(
//  //          new StandardCategoryLabelGenerator("{2}", labelFormatter)
//    //    );
//        renderer.setItemLabelsVisible(true);
//
//        return chart;
//    }
//
//    /**
//     * Starting point for the demo.
//     *
//     * @param args  ignored.
//     */
//    public static void main(final String[] args) {
//        final WaterfallChartDemo demo = new WaterfallChartDemo("Waterfall Chart Demo");
//        demo.pack();
//        RefineryUtilities.centerFrameOnScreen(demo);
//        demo.setVisible(true);
//    }
//    
//}
