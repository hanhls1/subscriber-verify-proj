use [3tverify]

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[StatusInfo](
	[id] [varchar](36) NOT NULL,
	[status_code] [varchar](10) NULL,
	[status] [nvarchar](255) NULL
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

-----------
INSERT [dbo].[StatusInfo] ([id], [status_code], [status]) VALUES (N'09ac75-049a-40d7-80ef-1978ab94d8', N'0', N'Thành Công')

INSERT [dbo].[StatusInfo] ([id], [status_code], [status]) VALUES (N'09mi75-049a-40d7-80ef-19785gu3f8', N'1', N'Tham số không đủ hoặc không hợp lệ')
INSERT [dbo].[StatusInfo] ([id], [status_code], [status]) VALUES (N'09dx75-049a-40d7-80ef-1978tb3vd8', N'2', N'Xảy ra lỗi khi request')
INSERT [dbo].[StatusInfo] ([id], [status_code], [status]) VALUES (N'09ac75-049a-40d7-80ef-19723de4d8', N'3', N'Request bị từ chối')
INSERT [dbo].[StatusInfo] ([id], [status_code], [status]) VALUES (N'09ac75-049a-40d7-80ef-196yob94d8', N'4', N'RequestID không tồn tại')
INSERT [dbo].[StatusInfo] ([id], [status_code], [status]) VALUES (N'09ac75-049a-40d7-80ef-197ac3d4d8', N'5', N'Lỗi chưa xác định')
