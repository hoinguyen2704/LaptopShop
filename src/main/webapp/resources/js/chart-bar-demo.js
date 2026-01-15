// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily =
  '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = "#292b2c";

// Grouped Bar Chart - 7 Days Orders by Status
var ctx = document.getElementById("myBarChart");
var myBarChart = new Chart(ctx, {
  type: "bar", // Vertical bars
  data: {
    labels: orderLabels, // Dates from JSP: ["14/01", "15/01", ...]
    datasets: [
      {
        label: "Hoàn thành",
        backgroundColor: "rgba(40, 167, 69, 0.8)", // Green
        borderColor: "rgba(40, 167, 69, 1)",
        borderWidth: 1,
        data: completedData, // COMPLETE counts
      },
      {
        label: "Hoàn/Hủy",
        backgroundColor: "rgba(220, 53, 69, 0.8)", // Red
        borderColor: "rgba(220, 53, 69, 1)",
        borderWidth: 1,
        data: failedData, // RETURNED + CANCELLED counts
      },
    ],
  },
  options: {
    layout: {
      padding: {
        top: 20,
        right: 15,
        bottom: 10,
        left: 15,
      },
    },
    scales: {
      xAxes: [
        {
          gridLines: {
            display: false,
          },
          ticks: {
            fontSize: 12,
          },
        },
      ],
      yAxes: [
        {
          ticks: {
            min: 0,
            max: Math.max(...completedData, ...failedData) * 1.2, // Add 20% headroom
            beginAtZero: true,
            stepSize: 1, // Integer steps
            callback: function (value) {
              if (Number.isInteger(value)) {
                return value;
              }
            },
          },
          gridLines: {
            display: true,
            color: "rgba(0, 0, 0, 0.05)",
          },
          scaleLabel: {
            display: true,
            labelString: "Số lượng đơn hàng",
          },
        },
      ],
    },
    legend: {
      display: true,
      position: "bottom",
      labels: {
        fontSize: 13,
        padding: 15,
      },
    },
    tooltips: {
      callbacks: {
        label: function (tooltipItem, data) {
          var dataset = data.datasets[tooltipItem.datasetIndex];
          var value = dataset.data[tooltipItem.index];
          return dataset.label + ": " + value + " đơn";
        },
      },
    },
  },
});
