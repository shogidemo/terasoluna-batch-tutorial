package org.terasoluna.batch.tutorial.dbaccess.tasklet;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import org.terasoluna.batch.tutorial.common.dto.MemberInfoDto;

@Component // (1)
public class PointAddTasklet implements Tasklet {

    private static final String TARGET_STATUS = "1"; // (2)

    private static final String INITIAL_STATUS = "0"; // (3)

    private static final String GOLD_MEMBER = "G"; // (4)

    private static final String NORMAL_MEMBER = "N"; // (5)

    private static final int MAX_POINT = 1000000; // (6)

    private static final int CHUNK_SIZE = 10; // (7)

    @Inject // (8)
    ItemStreamReader<MemberInfoDto> reader; // (9)

    @Inject // (8)
    ItemWriter<MemberInfoDto> writer; // (10)

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception { // (11)
        MemberInfoDto item = null;

        List<MemberInfoDto> items = new ArrayList<>(CHUNK_SIZE); // (12)

        try {
            reader.open(chunkContext.getStepContext().getStepExecution().getExecutionContext()); // (13)

            while ((item = reader.read()) != null) { // (14)

                if (TARGET_STATUS.equals(item.getStatus())) {
                    if (GOLD_MEMBER.equals(item.getType())) {
                        item.setPoint(item.getPoint() + 100);
                    } else if (NORMAL_MEMBER.equals(item.getType())) {
                        item.setPoint(item.getPoint() + 10);
                    }

                    if (item.getPoint() > MAX_POINT) {
                        item.setPoint(MAX_POINT);
                    }

                    item.setStatus(INITIAL_STATUS);
                }

                items.add(item);

                if (items.size() == CHUNK_SIZE) { // (15)
                    writer.write(items); // (16)
                    items.clear();
                }
            }

            writer.write(items); // (17)
        } finally {
            reader.close(); // (18)
        }

        return RepeatStatus.FINISHED; // (19)
    }
}
