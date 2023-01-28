import React, { useRef } from 'react';
import { Button, Upload, message } from 'antd';
import type { ProFormInstance } from '@ant-design/pro-form';
import ProForm, { ProFormText } from '@ant-design/pro-form';
import { UploadOutlined } from '@ant-design/icons';
import { useModel, useRequest } from 'umi';
import { getInformation, updateInformation } from '@/services/gemini-oauth2/user/user';

import styles from './BaseView.less';

// 头像组件 方便以后独立，增加裁剪之类的功能
const AvatarView = ({ avatar }: { avatar: string }) => (
  <>
    <div className={styles.avatar_title}>头像</div>
    <div className={styles.avatar}>
      <img src={avatar} alt="avatar" />
    </div>
    <Upload showUploadList={false}>
      <div className={styles.button_view}>
        <Button disabled>
          <UploadOutlined />
          更换头像
        </Button>
      </div>
    </Upload>
  </>
);

const BaseView: React.FC = () => {
  const formRef = useRef<ProFormInstance>();
  const { initialState, setInitialState } = useModel('@@initialState');

  const { data: currentUser, loading } = useRequest(() => {
    return getInformation(localStorage.getItem('userId')!);
  });

  const getAvatarURL = () => {
    if (currentUser) {
      if (currentUser.avatar) {
        return currentUser.avatar;
      }
      const url = 'https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png';
      return url;
    }
    return '';
  };

  const handleFinish = async (formData: API.User.UserInformationDTO) => {
    const messageKey = 'update';
    const userId = localStorage.getItem('userId')!;
    message.loading({ content: '请求处理中...', key: messageKey });
    updateInformation(userId, formData)
      .then((response: API.ApiResponse<API.User.UserInformationDTO>) => {
        formRef.current?.setFieldsValue(response.data);
        message.success({ content: '更新基本信息成功', key: messageKey });

        // 更新右上角昵称及头像
        initialState?.fetchUserInfo?.().then((userInfo: any) => {
          setInitialState((s: any) => ({
            ...s,
            currentUser: userInfo,
          }));
        });
      })
      .catch((error: any) => {
        message.error({ content: error.message, key: messageKey });
      });
  };
  return (
    <div className={styles.baseView}>
      {loading ? null : (
        <>
          <div className={styles.left}>
            <ProForm
              formRef={formRef}
              hideRequiredMark
              initialValues={{ ...currentUser }}
              layout="vertical"
              onFinish={handleFinish}
              submitter={{
                searchConfig: {
                  submitText: '更新基本信息',
                },
                render: (_, dom) => dom[1],
              }}
            >
              <ProFormText
                label="昵称"
                name="nickname"
                placeholder="请输入您的昵称"
                rules={[{ max: 16, required: true }]}
                tooltip="最长16位"
                width="md"
              />
              <ProFormText
                label="头像"
                name="avatar"
                placeholder="请输入您的头像"
                rules={[{ max: 128, required: true }]}
                tooltip="最长128位"
                width="lg"
              />
            </ProForm>
          </div>
          <div className={styles.right}>
            <AvatarView avatar={getAvatarURL()} />
          </div>
        </>
      )}
    </div>
  );
};

export default BaseView;
