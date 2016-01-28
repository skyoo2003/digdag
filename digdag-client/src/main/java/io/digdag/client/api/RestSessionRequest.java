package io.digdag.client.api;

import java.time.Instant;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import org.immutables.value.Value;
import io.digdag.client.config.Config;

@Value.Immutable
@JsonSerialize(as = ImmutableRestSessionRequest.class)
@JsonDeserialize(as = ImmutableRestSessionRequest.class)
public abstract class RestSessionRequest
{
    @JsonProperty("repository")
    public abstract String getRepositoryName();

    @JsonProperty("workflow")
    public abstract String getWorkflowName();

    public abstract Instant getInstant();

    public abstract Optional<String> getRetryAttemptName();

    //@JsonProperty("revision")
    //public abstract Optional<String> getRevision();

    //@JsonProperty("workflowId")
    //public abstract Optional<Integer> getWorkflowId();

    @JsonProperty("params")
    public abstract Config getParams();

    //@JsonProperty("from")
    //public abstract Optional<String> getFromTaskName();

    public static ImmutableRestSessionRequest.Builder builder()
    {
        return ImmutableRestSessionRequest.builder();
    }
}
