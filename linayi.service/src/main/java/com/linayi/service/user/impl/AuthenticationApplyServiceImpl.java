package com.linayi.service.user.impl;

import com.linayi.dao.area.SmallCommunityMapper;
import com.linayi.dao.supermarket.SupermarketMapper;
import com.linayi.dao.user.AuthenticationApplyMapper;
import com.linayi.dao.user.UserMapper;
import com.linayi.entity.area.SmallCommunity;
import com.linayi.entity.promoter.PromoterOrderMan;
import com.linayi.entity.spokesman.Spokesman;
import com.linayi.entity.supermarket.Supermarket;
import com.linayi.entity.user.AuthenticationApply;
import com.linayi.entity.user.User;
import com.linayi.enums.SpokesmanStatus;
import com.linayi.exception.ErrorType;
import com.linayi.service.promoter.OrderManMemberService;
import com.linayi.service.promoter.PromoterOrderManService;
import com.linayi.service.spokesman.SpokesmanService;
import com.linayi.service.user.AuthenticationApplyService;
import com.linayi.util.ImageUtil;
import com.linayi.util.OSSManageUtil;
import com.linayi.util.ResponseData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class AuthenticationApplyServiceImpl implements AuthenticationApplyService {

	@Resource
	private AuthenticationApplyMapper authenticationApplyMapper;

	@Resource
	private UserMapper userMapper;

	@Resource
	private SmallCommunityMapper smallCommunityMapper;

	@Resource
	private SpokesmanService spokesmanService;

	@Resource
	private SupermarketMapper supermarketMapper;
	
	@Resource
	private PromoterOrderManService promoterOrderManService;
	@Resource
	private OrderManMemberService orderManMemberService;


	@Override
	public Object applySharer(AuthenticationApply apply, MultipartFile[] file) {
/*		apply.setAuthenticationType("SHARER");
		AuthenticationApply authenticationApply1 = authenticationApplyMapper.getAuthenticationApplyByUserIdAndType(apply);
		if(authenticationApply1 == null){*/
			try {
				AuthenticationApply authenticationApply = new AuthenticationApply();
				authenticationApply.setRealName(apply.getRealName());
				authenticationApply.setMobile(apply.getMobile());
				authenticationApply.setUserId(apply.getUserId());
				authenticationApply.setIdCardFront(ImageUtil.handleUpload(file[0]));
				authenticationApply.setIdCardBack(ImageUtil.handleUpload(file[1]));
				authenticationApply.setCreateTime(new Date());
				authenticationApply.setUpdateTime(new Date());
				authenticationApply.setStatus("WAIT_AUDIT");
				authenticationApply.setAuthenticationType("SHARER");
				int rows = authenticationApplyMapper.insert(authenticationApply);
				if(rows != 1){
					return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ResponseData("操作成功！").toString();
/*		}else{
			if("SHARER".equals(authenticationApply1.getAuthenticationType())){
				return new ResponseData("F","已申请！").toString();
			}else{
				try {
					AuthenticationApply authenticationApply = new AuthenticationApply();
					authenticationApply.setRealName(apply.getRealName());
					authenticationApply.setMobile(apply.getMobile());
					authenticationApply.setUserId(apply.getUserId());
					authenticationApply.setIdCardFront(ImageUtil.handleUpload(file[0]));
					authenticationApply.setIdCardBack(ImageUtil.handleUpload(file[1]));
					authenticationApply.setCreateTime(new Date());
					authenticationApply.setUpdateTime(new Date());
					authenticationApply.setStatus("WAIT_AUDIT");
					authenticationApply.setAuthenticationType("SHARER");
					int rows = authenticationApplyMapper.insert(authenticationApply);
					if(rows != 1){
						return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return new ResponseData("操作成功！").toString();
			}
		}*/
	}

	@Override
	public List<AuthenticationApply> selectAuthenticationApplyList(AuthenticationApply apply) {
		String authenticationType = apply.getAuthenticationType();
		if("价格分享员".equals(authenticationType)){
			apply.setAuthenticationType("SHARER");
		}else if("采买员".equals(authenticationType)){
			apply.setAuthenticationType("PROCURER");
		}else if("配送员".equals(authenticationType)){
			apply.setAuthenticationType("DELIVERER");
		}else if("代言人".equals(authenticationType)){
			apply.setAuthenticationType("SPOKESMAN");
		}else if("家庭服务师".equals(authenticationType)){
			apply.setAuthenticationType("ORDER_MAN");
		}
		List<AuthenticationApply> list = authenticationApplyMapper.selectAuthenticationApplyList(apply);
		for (AuthenticationApply authenticationApply : list) {
			if(authenticationApply.getSupermarketId()!=null){
				Supermarket supermarket = supermarketMapper.selectSupermarketBysupermarketId(authenticationApply.getSupermarketId());
				authenticationApply.setSupermarketName(supermarket.getName());
			}
		}
		return list;
	}

	@Override
	public AuthenticationApply getAuthenticationApplyByapplyId(Integer applyId) {
		AuthenticationApply apply = authenticationApplyMapper.getAuthenticationApplyByapplyId(applyId);
		if(apply.getSupermarketId()!=null){
			Supermarket supermarket = supermarketMapper.selectSupermarketBysupermarketId(apply.getSupermarketId());
			apply.setSupermarketName(supermarket.getName());
		}
		return apply;
	}

	@Transactional
	@Override
	public void updateAuthenticationApplyAndUserInfo(AuthenticationApply apply) {
		if("家庭服务师".equals(apply.getAuthenticationType())){
//旧的家庭服务师审核方法	promoterOrderManService.applyOrderManInWeb(apply.getStatus(), apply.getApplyId(), apply.getUserId(), apply.getPromoterId(), apply.getIdentity());
			AuthenticationApply authenticationApply = authenticationApplyMapper.selectByApplyId(apply);
			authenticationApply.setAuditStr(apply.getStatus());
			promoterOrderManService.auditOrderMan(authenticationApply);
		}
		if("AUDIT_SUCCESS".equals(apply.getStatus())&&!"家庭服务师".equals(apply.getAuthenticationType())){
			if("会员".equals(apply.getAuthenticationType())){
				apply.setAuditStr(apply.getStatus());
				orderManMemberService.auditMember(apply);
			} else if("价格分享员".equals(apply.getAuthenticationType())){
				AuthenticationApply authenticationApply = new AuthenticationApply();
				authenticationApply.setApplyId(apply.getApplyId());
				authenticationApply.setStatus(apply.getStatus());
				authenticationApply.setUpdateTime(new Date());
				authenticationApplyMapper.updateStatusByApplyId(authenticationApply);
				User user = new User();
				user.setIdCardBack(apply.getIdCardBack());
				user.setIdCardFront(apply.getIdCardFront());
				user.setUserId(apply.getUserId());
				user.setMobile(apply.getMobile());
				user.setUpdateTime(new Date());
				user.setIsSharer("TRUE");
				user.setRealName(apply.getRealName());
				userMapper.updateUserByuserId(user);
			}else if("采买员".equals(apply.getAuthenticationType())){
				AuthenticationApply authenticationApply = new AuthenticationApply();
				authenticationApply.setApplyId(apply.getApplyId());
				authenticationApply.setStatus(apply.getStatus());
				authenticationApply.setUpdateTime(new Date());
				authenticationApplyMapper.updateStatusByApplyId(authenticationApply);
				User user = new User();
				user.setIdCardBack(apply.getIdCardBack());
				user.setIdCardFront(apply.getIdCardFront());
				user.setUserId(apply.getUserId());
				user.setMobile(apply.getMobile());
				user.setUpdateTime(new Date());
				user.setIsProcurer("TRUE");
				user.setRealName(apply.getRealName());
				userMapper.updateUserByuserId(user);
				Supermarket supermarket1 = new Supermarket();
				supermarket1.setProcurerId(apply.getUserId());
				List<Supermarket> selectAll = supermarketMapper.selectAll(supermarket1);
				if(selectAll.size() == 1){
					Supermarket supermarket3 = selectAll.stream().findFirst().orElse(null);
					Supermarket supermarket2 = new Supermarket();
					supermarket2.setSupermarketId(supermarket3.getSupermarketId());
					supermarket2.setProcurerId(null);
					supermarketMapper.updateSupermarketBysupermarketId(supermarket2);
				}
				Supermarket supermarket = new Supermarket();
				supermarket.setSupermarketId(apply.getSupermarketId());
				supermarket.setProcurerId(apply.getUserId());
				supermarketMapper.updateSupermarketBysupermarketId(supermarket);
			}else if("配送员".equals(apply.getAuthenticationType())){
				AuthenticationApply authenticationApply = new AuthenticationApply();
				authenticationApply.setApplyId(apply.getApplyId());
				authenticationApply.setStatus(apply.getStatus());
				authenticationApply.setUpdateTime(new Date());
				authenticationApplyMapper.updateStatusByApplyId(authenticationApply);
/*				SmallCommunity smallCommunity1 = new SmallCommunity();
				smallCommunity1.setSmallCommunityId(apply.getUserId());
				SmallCommunity smallCommunity2 = smallCommunityMapper.getSmallCommunity(smallCommunity1);
				if(smallCommunity2!=null){
					SmallCommunity smallCommunity3 = new SmallCommunity();
					smallCommunity3.setDelivererId(null);
					smallCommunity3.setSmallCommunityId(smallCommunity2.getSmallCommunityId());
					smallCommunityMapper.updateSmallCommunity(smallCommunity3);
				}*/
				SmallCommunity smallCommunity = new SmallCommunity();
				smallCommunity.setDelivererId(apply.getUserId());
				smallCommunity.setSmallCommunityId(apply.getSmallCommunityId());
				smallCommunity.setUpdateTime(new Date());
				smallCommunityMapper.updateSmallCommunity(smallCommunity);
			}else if("代言人".equals(apply.getAuthenticationType())){
				AuthenticationApply authenticationApply = new AuthenticationApply();
				authenticationApply.setApplyId(apply.getApplyId());
				authenticationApply.setStatus(apply.getStatus());
				authenticationApply.setUpdateTime(new Date());
				authenticationApplyMapper.updateStatusByApplyId(authenticationApply);
				Spokesman currentSpokesman = spokesmanService.selectSpokesmanByUserId(apply.getUserId());
				if(currentSpokesman == null){
					AuthenticationApply currentApply = authenticationApplyMapper.getAuthenticationApplyByapplyId(apply.getApplyId());
					//添加代言人
					Spokesman spokesman = new Spokesman();
					spokesman.setUserId(currentApply.getUserId());
					spokesman.setNickname(currentApply.getNickname());
					spokesman.setRealName(currentApply.getRealName());
					spokesman.setMobile(currentApply.getMobile());
					spokesman.setAreaCode(currentApply.getAreaCode());
					spokesman.setSmallCommunityId(currentApply.getSmallCommunityId());
					spokesman.setAddress(currentApply.getAddress());
					spokesman.setSpecialty(currentApply.getSpecialty());
					spokesman.setHobby(currentApply.getHobby());
					spokesman.setMotto(currentApply.getMotto());
					spokesman.setImage(currentApply.getImage());
					spokesman.setStatus(SpokesmanStatus.ENABLED.toString());
					spokesman.setCreateTime(new Date());
					spokesmanService.addSpokesman(spokesman);
				}else{
					if(SpokesmanStatus.DISABLED.toString().equals(currentSpokesman.getStatus())){
						Spokesman newSpokesman = new Spokesman();
						newSpokesman.setUserId(currentSpokesman.getUserId());
						newSpokesman.setStatus(SpokesmanStatus.ENABLED.toString());
						spokesmanService.updateSpokesmanStatus(newSpokesman);
					}
				}

			}
		}else if("AUDIT_FAIL".equals(apply.getStatus())&&!"家庭服务师".equals(apply.getAuthenticationType())){
			AuthenticationApply authenticationApply = new AuthenticationApply();
			authenticationApply.setApplyId(apply.getApplyId());
			authenticationApply.setStatus(apply.getStatus());
			authenticationApply.setUpdateTime(new Date());
			authenticationApplyMapper.updateStatusByApplyId(authenticationApply);
			User user1 = userMapper.selectUserByuserId(apply.getUserId());


			if("TRUE".equals(user1.getIsSharer())){
				User user = new User();
				user.setUserId(apply.getUserId());
				user.setUpdateTime(new Date());
				user.setIsSharer("FALSE");
				userMapper.updateUserByuserId(user);
			}else if("TRUE".equals(user1.getIsProcurer())){
				User user = new User();
				user.setUserId(apply.getUserId());
				user.setUpdateTime(new Date());
				user.setIsProcurer("FALSE");
				userMapper.updateUserByuserId(user);
			}else if("TRUE".equals(user1.getIsMember())){
				User user = new User();
				user.setUserId(apply.getUserId());
				user.setUpdateTime(new Date());
				user.setIsMember("FALSE");
				userMapper.updateUserByuserId(user);
			}
			Spokesman currentSpokesman = spokesmanService.selectSpokesmanByUserId(apply.getUserId());
			if(currentSpokesman != null){
				if(SpokesmanStatus.ENABLED.toString().equals(currentSpokesman.getStatus())){
					Spokesman newSpokesman = new Spokesman();
					newSpokesman.setUserId(currentSpokesman.getUserId());
					newSpokesman.setStatus(SpokesmanStatus.DISABLED.toString());
					spokesmanService.updateSpokesmanStatus(newSpokesman);
				}
			}

		}
	}

	@Override
	public Object applyProcurer(AuthenticationApply apply, MultipartFile[] file) {
/*		apply.setAuthenticationType("PROCURER");
		AuthenticationApply authenticationApply1 = authenticationApplyMapper.getAuthenticationApplyByUserIdAndType(apply);
		if(authenticationApply1 == null){*/
			try {
				AuthenticationApply authenticationApply = new AuthenticationApply();
				authenticationApply.setAddress(apply.getAddress());
				authenticationApply.setSupermarketId(apply.getSupermarketId());
				authenticationApply.setAreaCode(apply.getAreaCode());
				authenticationApply.setRealName(apply.getRealName());
				authenticationApply.setMobile(apply.getMobile());
				authenticationApply.setUserId(apply.getUserId());
				authenticationApply.setIdCardFront(ImageUtil.handleUpload(file[0]));
				authenticationApply.setIdCardBack(ImageUtil.handleUpload(file[1]));
				authenticationApply.setCreateTime(new Date());
				authenticationApply.setUpdateTime(new Date());
				authenticationApply.setStatus("WAIT_AUDIT");
				authenticationApply.setAuthenticationType("PROCURER");
				int rows = authenticationApplyMapper.insert(authenticationApply);
				if(rows != 1){
					return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ResponseData("操作成功！").toString();
/*		}else{
			if("PROCURER".equals(authenticationApply1.getAuthenticationType())){
				return new ResponseData("F","已申请！").toString();
			}else{
				try {
					AuthenticationApply authenticationApply = new AuthenticationApply();
					authenticationApply.setAddress(apply.getAddress());
					authenticationApply.setSupermarketId(apply.getSupermarketId());
					authenticationApply.setAreaCode(apply.getAreaCode());
					authenticationApply.setRealName(apply.getRealName());
					authenticationApply.setMobile(apply.getMobile());
					authenticationApply.setUserId(apply.getUserId());
					authenticationApply.setIdCardFront(ImageUtil.handleUpload(file[0]));
					authenticationApply.setIdCardBack(ImageUtil.handleUpload(file[1]));
					authenticationApply.setCreateTime(new Date());
					authenticationApply.setUpdateTime(new Date());
					authenticationApply.setStatus("WAIT_AUDIT");
					authenticationApply.setAuthenticationType("PROCURER");
					int rows = authenticationApplyMapper.insert(authenticationApply);
					if(rows != 1){
						return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return new ResponseData("操作成功！").toString();
			}
		}*/
	}

	@Override
	public Object applyDeliverer(AuthenticationApply apply, MultipartFile[] file) {
/*		apply.setAuthenticationType("DELIVERER");
		AuthenticationApply authenticationApply1 = authenticationApplyMapper.getAuthenticationApplyByUserIdAndType(apply);
		if(authenticationApply1 == null){*/
			try {
				AuthenticationApply authenticationApply = new AuthenticationApply();
				authenticationApply.setAddress(apply.getAddress());
				authenticationApply.setSmallCommunityId(apply.getSmallCommunityId());
				authenticationApply.setRealName(apply.getRealName());
				authenticationApply.setMobile(apply.getMobile());
				authenticationApply.setUserId(apply.getUserId());
				authenticationApply.setIdCardFront(ImageUtil.handleUpload(file[0]));
				authenticationApply.setIdCardBack(ImageUtil.handleUpload(file[1]));
				authenticationApply.setCreateTime(new Date());
				authenticationApply.setUpdateTime(new Date());
				authenticationApply.setStatus("WAIT_AUDIT");
				authenticationApply.setAuthenticationType("DELIVERER");
				int rows = authenticationApplyMapper.insert(authenticationApply);
				if(rows != 1){
					return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ResponseData("操作成功！").toString();
/*		}else{
			if("DELIVERER".equals(authenticationApply1.getAuthenticationType())){
				return new ResponseData("F","已申请！").toString();
			}else{
				try {
					AuthenticationApply authenticationApply = new AuthenticationApply();
					authenticationApply.setAddress(apply.getAddress());
					authenticationApply.setSmallCommunityId(apply.getSmallCommunityId());
					authenticationApply.setRealName(apply.getRealName());
					authenticationApply.setMobile(apply.getMobile());
					authenticationApply.setUserId(apply.getUserId());
					authenticationApply.setIdCardFront(ImageUtil.handleUpload(file[0]));
					authenticationApply.setIdCardBack(ImageUtil.handleUpload(file[1]));
					authenticationApply.setCreateTime(new Date());
					authenticationApply.setUpdateTime(new Date());
					authenticationApply.setStatus("WAIT_AUDIT");
					authenticationApply.setAuthenticationType("DELIVERER");
					int rows = authenticationApplyMapper.insert(authenticationApply);
					if(rows != 1){
						return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return new ResponseData("操作成功！").toString();
			}
		}*/
	}



//	@Override
//	public Object applySpokesman(AuthenticationApply apply, MultipartFile file) {
//		apply.setAuthenticationType("SPOKESMAN");
//		AuthenticationApply authenticationApply = authenticationApplyMapper.getAuthenticationApplyByUserIdAndType(apply);
//		if(authenticationApply == null) {
//			try {
//				Date now = new Date();
//				apply.setStatus("WAIT_AUDIT");
//				apply.setAuthenticationType("SPOKESMAN");
//				apply.setCreateTime(now);
//				apply.setUpdateTime(now);
//				apply.setImage(ImageUtil.handleUpload(file));
//				authenticationApplyMapper.insert(apply);
//				return new ResponseData("操作成功！").toString();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}else{
//			if("SPOKESMAN".equals(authenticationApply.getAuthenticationType())){
//				return new ResponseData("F","已申请！").toString();
//			}
//			return new ResponseData("操作成功！").toString();
//		}
//		return null;
//	}

	@Override
	public Object applyOrderMan(AuthenticationApply apply, MultipartFile[] file) {
		try {
			AuthenticationApply authenticationApply = new AuthenticationApply();
			authenticationApply.setAddress(apply.getAddress());
			authenticationApply.setRealName(apply.getRealName());
			authenticationApply.setMobile(apply.getMobile());
			authenticationApply.setUserId(apply.getUserId());
			authenticationApply.setIdCardFront(ImageUtil.handleUpload(file[0]));
			authenticationApply.setIdCardBack(ImageUtil.handleUpload(file[1]));
			authenticationApply.setCreateTime(new Date());
			authenticationApply.setUpdateTime(new Date());
			authenticationApply.setStatus("WAIT_AUDIT");
			authenticationApply.setAuthenticationType("ORDER_MAN");
			int rows = authenticationApplyMapper.insert(authenticationApply);
			if(rows != 1){
				return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseData("操作成功！").toString();
	}

	@Override
	public String save(AuthenticationApply authenticationApply) {
		try {
			authenticationApplyMapper.update(authenticationApply);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "fail";
	}


}
