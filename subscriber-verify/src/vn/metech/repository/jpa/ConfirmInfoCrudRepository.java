package vn.metech.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import vn.metech.common.MessageStatus;
import vn.metech.common.RecordStatus;
import vn.metech.common.ServiceType;
import vn.metech.dto.RequestWithKey;
import vn.metech.dto.monitor.ConfirmInfoDto;
import vn.metech.dto.response.PartnerResponse;
import vn.metech.entity.ConfirmInfo;

import java.util.Date;
import java.util.List;

public interface ConfirmInfoCrudRepository extends CrudRepository<ConfirmInfo, String> {

    @Modifying
    @Transactional
    @Query("update ConfirmInfo set fetchTimes = (fetchTimes + 1), lastFetch = ?1 where id in (?2)")
    void updateFetchData(Date lastFetch, List<String> ids);

    long countByRecordStatusAndMessageStatusIn(RecordStatus recordStatus, List<MessageStatus> messageStatuses);

    Page<ConfirmInfo> getByRecordStatusAndMessageStatusIn(
            RecordStatus recordStatus, List<MessageStatus> messageStatuses, Pageable pageable);

    /**
     * Thời gian timeout của tin nhắn chờ khách hàng phản hồi là 2 days
     */
//    @Query("select id from ConfirmInfo " +
//            "where recordStatus = 'REQUESTING' and DATEDIFF(day, createdDate, CURRENT_TIMESTAMP) < 2")
//    Page<String> findMessageFetchableConfirmInfoIdsBy(Pageable pageable);
    @Query("select ci from ConfirmInfo ci where ci.recordStatus = ?1")
    Page<ConfirmInfo> findMessageProcessibleBy(RecordStatus recordStatus, Pageable pageable);

    @Query("select count(ci) from ConfirmInfo ci where ci.recordStatus = ?1")
    long countByRecordStatus(RecordStatus recordStatus);

    @Query("select count(ci) from ConfirmInfo ci " +
            "where ci.phoneNumber = ?1 and ci.serviceType = ?2 and ci.createdBy in (?3)")
    Long countConfirmInfoBy(String phoneNumber, ServiceType serviceType, List<String> userIds);

    long countByPhoneNumberAndServiceTypeAndCreatedByIn(
            String phoneNumber, ServiceType serviceType, List<String> userIds);

    long countByPhoneNumberAndServiceType(String phoneNumber, ServiceType serviceType);

    ConfirmInfo findFirstByPhoneNumberAndServiceTypeAndCreatedByInOrderByCreatedDateDesc(
            String phoneNumber, ServiceType serviceType, List<String> userIds);

    ConfirmInfo findFirstByPhoneNumberAndServiceTypeOrderByCreatedDateDesc(String phoneNumber, ServiceType serviceType);

    @Query("select ci.id from ConfirmInfo ci where ci.serviceType in " +
            "('FC_TPC_REF_PHONE', 'FC_BS_IMEI_02', 'FC_BS_IMEI_01') " +
            "and ci.aggregateStatus = -1 and ci.createdBy in (?1)")
    Page<String> findAggregateConfirmInfoBy(List<String> tpcUserIds, Pageable pageable);

    @Query("select ci.id from ConfirmInfo ci where ci.aggregateStatus = ?1")
    Page<String> findInitedConfirmInfoIdsByAggregateStatus(int status, Pageable pageable);

    @EntityGraph(attributePaths = {"params", "confirmInfoReceives"})
    Iterable<ConfirmInfo> findIncludeParamsAndConfirmInfoReceivesByIdIn(List<String> ids);

    @EntityGraph(attributePaths = {"params"})
    Iterable<ConfirmInfo> findIncludeParamsByIdIn(List<String> confirmIds);

    Page<ConfirmInfo> findByRecordStatusIn(List<RecordStatus> recordStatuses, Pageable pageable);

    @Query("select new vn.metech.dto.RequestWithKey(ci.customerCode, ci.account, ci.secureKey) " +
            "from ConfirmInfo ci where ci.recordStatus in (?1) and ci.customerCode is not null " +
            "and  (ci.lastFetch is null or ci.lastFetch > ?2 )")
    Page<RequestWithKey> findRequestWithKeyBy(List<RecordStatus> recordStatuses, Date fetchAfter, Pageable pageable);

    Page<ConfirmInfo> findByRecordStatusAndMessageStatus(RecordStatus recordStatus, MessageStatus messageStatus, Pageable pageable);

    @Query("select ci.id from ConfirmInfo ci where ci.recordStatus = ?1 and (ci.lastFetch is null or ci.lastFetch > ?2)")
    Page<String> findIdsByRecordStatusAndLastFetch(RecordStatus recordStatus, Date fetchAfter, Pageable pageable);


    //created    order by c.id asc limit ?1,  ?2 ", nativeQuery = true
    @Query(value = "select new vn.metech.dto.monitor.ConfirmInfoDto(ci.requestId, ci.phoneNumber, ci.statusCode, ci.status, ci.serviceType, ci.createdDate, ci.account, ci.partnerName, ci.partnerId ) " +
            "from ConfirmInfo ci where ci.status is not null")
    Page<ConfirmInfoDto> findByStatus(Date fetchAfter, Pageable pageable);

    @Query(value = "select new vn.metech.dto.monitor.ConfirmInfoDto(ci.requestId, ci.phoneNumber, ci.statusCode, ci.status, ci.serviceType, ci.createdDate, ci.account, ci.partnerName, ci.partnerId) " +
            "from ConfirmInfo ci where ci.status is not null and ci.phoneNumber = ?1")
    Page<ConfirmInfoDto> findByStatusAndPhone(String phoneNumber, Pageable pageable);

    @Query("select DISTINCT ci.serviceType from ConfirmInfo ci where ci.serviceType is not null ORDER BY ci.serviceType")
    List<String> getConfirmInfoServicesByService () ;

    @Query("select new vn.metech.dto.response.PartnerResponse( ci.partnerId, ci.partnerName)" +
            " from ConfirmInfo ci where ci.partnerId is not null group by ci.partnerId, ci.partnerName ORDER BY ci.partnerName")
    List<PartnerResponse>  getConfirmInfoPartnerIdBy();

}
