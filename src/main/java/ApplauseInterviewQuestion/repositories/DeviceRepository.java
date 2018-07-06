package ApplauseInterviewQuestion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ApplauseInterviewQuestion.pojo.Device;

public interface DeviceRepository extends JpaRepository<Device, Long>, DeviceRepositoryCustom{
	public Device findByDescription(String description);
}
