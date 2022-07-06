package br.com.datasind.job;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 
 * @author OsmarJunior
 * @since 25 de nov de 2015
 */

public class QuartzJobListener implements ServletContextListener {

	private Scheduler scheduler;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent ctx) {
		JobDetail job = JobBuilder.newJob(AutoLimpezaCertidoesPdfJOB.class)
				.withIdentity("dummyJobName", "group1").build();

		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity("dummyTriggerName", "group1")
				.withSchedule(
						SimpleScheduleBuilder.simpleSchedule()
								.withIntervalInSeconds(1).repeatForever())
				.build();
		// System.out.println(ctx.getServletContext().getAttributeNames());
		try {
			scheduler = ((StdSchedulerFactory) ctx.getServletContext().getAttribute(QuartzInitializerListener.QUARTZ_FACTORY_KEY)).getScheduler();
			scheduler.getContext().put("contexto", ctx);
			scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {

		}

	}

}