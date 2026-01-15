// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily =
  '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = "#292b2c";

// Horizontal Bar Chart - Top 10 Best Selling Products
var ctx = document.getElementById("topProductsChart");
var topProductsChart = new Chart(ctx, {
  type: "horizontalBar", // Horizontal bars
  data: {
    labels: productNames, // Product names from JSP
    datasets: [
      {
        label: "Số lượng đã bán",
        backgroundColor: "rgba(54, 162, 235, 0.8)", // Blue
        borderColor: "rgba(54, 162, 235, 1)",
        borderWidth: 1,
        data: productSoldCounts, // Sold counts from JSP
        datalabels: {
          color: '#fff',
          font: {
            size: 14,
            weight: 'bold'
          },
          anchor: 'center',
          align: 'center',
          formatter: function(value) {
            return value + ' đã bán';
          }
        }
      },
    ],
  },
  options: {
    plugins: {
      datalabels: {
        display: true,
        color: "#fff",
        font: {
          size: 14,
          weight: "bold",
        },
        anchor: "center",
        align: "center",
        // clip: true,  // Hide labels that would appear outside bars
        formatter: function (value) {
          return value + " đã bán";
        },
      },
    },
    scales: {
      xAxes: [
        {
          ticks: {
            display: false,  // Hide tick labels (numbers at bar ends)
            min: 0,
            beginAtZero: true,
            stepSize: 1, // Integer steps
          },
          gridLines: {
            display: true,
            color: "rgba(0, 0, 0, 0.05)",
          },
          scaleLabel: {
            display: true,
            labelString: "Số lượng đã bán",
          },
        },
      ],
      yAxes: [
        {
          gridLines: {
            display: false,
          },
          ticks: {
            fontSize: 11,
          },
        },
      ],
    },
    legend: {
      display: false,
    },
    tooltips: {
      enabled: false  // Disable tooltips to remove black numbers
    },
  },
});
