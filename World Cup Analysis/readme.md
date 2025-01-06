# Analyzing the FIFA World Cup Tournament - Data Science Portfolio Project Example

## Project Overview: 
This project focuses on using Python to explore and analyze men's FIFA World Cup matches, with the goal of answering the following questions:

- Do teams perform better when playing as the host team in the men's FIFA World Cup tournament?
- Who were the top goal scorers individually and by team?
- Are teams who perform better in the 1st or 2nd half more victorious?

The project code and results are contained in the Jupyter Notebook `football-results.ipynb`, with a summary of the conclusions below.

## Data: 
The data used in this project is spread across three CSV files stored in the `datasets` directory:
- `results.csv` contains match results for over 44,000 international football matches
- `shootouts.csv` contains the results of matches that ended in penalty shootouts
- `goalscorers.csv` contains data on the goal scorers from specific matches

Credit: All three datasets are can be obtained from Kaggle or GitHub using the following links
- https://www.kaggle.com/datasets/martj42/international-football-results-from-1872-to-2017
- https://github.com/martj42/international_results

Thanks to the dataset author for releasing this data into the public domain.

## Conclusion: 
In summary, this project provides valuable insights into the performances of teams in the men's FIFA World Cup tournament. Here are some key findings from the analysis:
- Only two teams have a better win-loss record in the World Cup as non-hosts than as hosts (Brazil and Spain)
- The top 5 all-time leading World Cup scorers (as of 2023) are Klose (Germany-16), Ronaldo (Brazil-15), Müller	(West Germany-14), Messi (Argentina-13), and Fontaine (France-13)
- Teams with the highest win percentages had a more balanced scoring performance across both the 1st and 2nd half whereas teams that scored most of their goals in one half over the other had much lower win percentages

There are a couple of caveats to these conclusions that we can address in future analyses which include:
- win percentage is not a perfect measure of tournament success
- taking into account the quality/strength of opponents
- varying team performance over time due to different coaches, players, and current meta-strategies


