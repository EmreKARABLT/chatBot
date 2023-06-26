import csv
import matplotlib.pyplot as plt

# Data
steps = []
train_loss = []
val_loss = []

# Read the CSV file
with open('graph.csv', 'r') as file:
    # Create a CSV reader object
    csv_reader = csv.reader(file)

    # Skip the header row if it exists
    next(csv_reader)

    # Read the data row by row
    for row in csv_reader:
        steps.append(int(row[0]))
        train_loss.append(float(row[1]))
        val_loss.append(float(row[2]))

# Plotting
plt.plot(steps, train_loss, label='Train Loss')
plt.plot(steps, val_loss, label='Validation Loss')

# Set labels and title
plt.xlabel('Step')
plt.ylabel('Loss')
plt.title('Train Loss vs. Validation Loss')

# Add a legend
plt.legend()

# Display the plot
plt.show()

