<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:templetepage>
	<jsp:attribute name="header">     
    </jsp:attribute>
    <jsp:attribute name="footer">     
    </jsp:attribute>
	<jsp:body>					
						
									<div id='ctl06_portlet_43f9ded1-4c3b-4b8b-8d8a-01a3ec30711c'
							style="float: left;">
										<div>
											<style>
</style>
											<div id="main">
												<div id="head"></div>
												<div id="head-link"></div>
												<div id="left"></div>
												<div id="content"></div>
												<div id="right"></div>
												<div id="footer"></div>
											</div>

										</div>
									</div>
									<div id='ctl06_portlet_a956cccc-dea3-4440-82a1-2684967a114f'
							style="float: left;">
										<div>
											<p class="MsoNormal"
									style="margin: 6pt 0cm 0.0001pt; text-align: justify;">
												<table style="text-align: justify;">
													<tbody>
														<tr>
															<td
													style="border: 1px solid #c00000; background-color: #dbe5f1;">
																<div style="text-align: center;">
																	<br /> <strong><span style="color: #0070c0;">${p.getTieuDe()}</span></strong>
																</div> <span style="text-align: justify;"><br />
																	&nbsp; <span style="font-size: 16px;">&nbsp;
																	${p.getNoiDung()}
																		
																		<br />
																</span></span><span style="font-size: 16px;"><span/>
														
												
															</td>
														</tr>
													</tbody>
												</table>
												<br /> <br />
											</p>
								
										</div>
									</div>		
									
									
									<c:if test="${sessionScope.login}">	
											<a href="/editpage/1"><img  src="/resources/icon/edit.png"/></a>
									</c:if>
			</jsp:body>
	
</t:templetepage>
