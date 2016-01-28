package io.digdag.cli.client;

import java.util.List;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import io.digdag.cli.SystemExitException;
import io.digdag.client.DigdagClient;
import io.digdag.client.api.RestSchedule;
import static io.digdag.cli.Main.systemExit;

public class ShowSchedule
    extends ClientCommand
{
    @Override
    public void mainWithClientException()
        throws Exception
    {
        switch (args.size()) {
        case 0:
            showSchedules();
            break;
        default:
            throw usage(null);
        }
    }

    public SystemExitException usage(String error)
    {
        System.err.println("Usage: digdag schedules");
        System.err.println("  Options:");
        ClientCommand.showCommonOptions();
        return systemExit(error);
    }

    public void showSchedules()
        throws Exception
    {

        Instant now = Instant.now();

        DigdagClient client = buildClient();
        ln("Schedules:");
        int count = 0;
        for (RestSchedule sched : client.getSchedules()) {
            ln("  id: %d", sched.getId());
            ln("  repository: %s", sched.getRepository().getName());
            ln("  workflow: %s", sched.getWorkflowName());
            ln("  next session time: %s", formatTime(sched.getNextScheduleTime()));
            ln("  next runs at: %s (%s later)", formatTime(sched.getNextRunTime()), formatTimeDiff(now, sched.getNextRunTime()));
            ln("");
            count++;
        }
        ln("%d entries.", count);
        System.err.println("Use `digdag workflows +NAME` to show workflow details.");
    }

    private static String formatTimeDiff(Instant now, long from)
    {
        long seconds = now.until(Instant.ofEpochSecond(from), ChronoUnit.SECONDS);
        long hours = seconds / 3600;
        seconds %= 3600;
        long minutes = seconds / 60;
        seconds %= 60;
        if (hours > 0) {
            return String.format("%2dh %2dm %2ds", hours, minutes, seconds);
        }
        else if (minutes > 0) {
            return String.format("    %2dm %2ds", minutes, seconds);
        }
        else {
            return String.format("        %2ds", seconds);
        }
    }
}
