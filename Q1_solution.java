class Solution {
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int numJobs = profit.length;
        Job[] jobs = new Job[numJobs];

        for (int i = 0; i < numJobs; ++i) {
            jobs[i] = new Job(endTime[i], startTime[i], profit[i]); // merging all the three array values int one array
        }

        Arrays.sort(jobs, Comparator.comparingInt(job -> job.endTime)); // sorting on the basis of job end time
        int[] dp = new int[numJobs + 1]; // creating a dp array, and initialized its value as zero

        for (int i = 0; i < numJobs; ++i) {
            int endTimeValue = jobs[i].endTime;
            int startTimeValue = jobs[i].startTime;
            int profitValue = jobs[i].profit;

            int latestNonConflictJobIndex = upperBound(jobs, i, startTimeValue); // calling binary search function to return the latest non conflicting job index
            dp[i + 1] = Math.max(dp[i], dp[latestNonConflictJobIndex] + profitValue); // for every job at index i, calculatedthe max profit adding the current job , versus not including.
        // if sum is greater than the maximum profit without current job, updated the dp with the larger value,
          return dp[numJobs];
    }

    private int upperBound(Job[] jobs, int endIndex, int targetTime) { // find the closest job that finished before the current job's start
        int low = 0;
        int high = endIndex;

        while (low < high) {
            int mid = (low + high) / 2;
            if (jobs[mid].endTime <= targetTime) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
// binary search to find the insertion point of the current job’s start time in the sorted jobs array to ensure no overlap.
        return low;
    }

    private static class Job {
        int endTime;
        int startTime;
        int profit;

        public Job(int endTime, int startTime, int profit) {
            this.endTime = endTime;
            this.startTime = startTime;
            this.profit = profit;
        }
    }
}
