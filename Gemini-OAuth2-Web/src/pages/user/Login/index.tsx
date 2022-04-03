import React from 'react';
import { message } from 'antd';
import { history, useModel } from 'umi';
import { ProFormCheckbox, ProFormText, LoginForm } from '@ant-design/pro-form';
import { LockOutlined, UserOutlined } from '@ant-design/icons';
import Footer from '@/components/Footer';
import styles from './index.less';
import { login } from '@/services/gemini-oauth2/user';

const Login: React.FC = () => {
  const { initialState, setInitialState } = useModel('@@initialState');

  const fetchUserInfo = async () => {
    const userInfo = await initialState?.fetchUserInfo?.();

    if (userInfo) {
      await setInitialState((s: any) => ({ ...s, currentUser: userInfo }));
    }
  };

  const handleSubmit = async (values: API.LoginParams) => {
    const userLoginQuery: API.User.UserLoginQuery = {
      username: values.username!,
      password: values.password!,
    };
    const response = await login(userLoginQuery);
    console.log(response);

    if (response.code === 100) {
      const defaultLoginSuccessMessage = '登录成功！';
      message.success(defaultLoginSuccessMessage);
      localStorage.setItem('userId', response.data.userId);
      localStorage.setItem('accessTokenId', response.data.accessToken.id);
      localStorage.setItem('accessToken', response.data.accessToken.secret);

      await fetchUserInfo();

      // 向redirect跳转并传递其余query参数
      if (!history) return;
      const { query } = history.location;
      const { redirect } = query as {
        redirect: string;
      };
      if (redirect) {
        delete query.redirect;
        history.push({
          pathname: redirect,
          query: query,
        });
      } else {
        history.push('/');
      }
    }
  };

  return (
    <div className={styles.container}>
      <div className={styles.content}>
        <LoginForm
          logo={<img alt="logo" src="/logo.svg" />}
          title="双子身份验证中心"
          subTitle={'集成统一快速验证身份'}
          initialValues={{
            autoLogin: true,
          }}
          onFinish={async (values) => {
            await handleSubmit(values as API.LoginParams);
          }}
        >
          <>
            <ProFormText
              name="username"
              fieldProps={{
                size: 'large',
                prefix: <UserOutlined className={styles.prefixIcon} />,
              }}
              placeholder={'用户名/电子邮箱/手机号码'}
              rules={[
                {
                  required: true,
                  message: '用户名是必填项！',
                },
              ]}
            />
            <ProFormText.Password
              name="password"
              fieldProps={{
                size: 'large',
                prefix: <LockOutlined className={styles.prefixIcon} />,
              }}
              placeholder={'密码'}
              rules={[
                {
                  required: true,
                  message: '密码是必填项！',
                },
              ]}
            />
          </>

          <div
            style={{
              marginBottom: 24,
            }}
          >
            <ProFormCheckbox noStyle name="autoLogin">
              自动登录
            </ProFormCheckbox>
            <a
              style={{
                float: 'right',
              }}
            >
              忘记密码 ?
            </a>
          </div>
        </LoginForm>
      </div>
      <Footer />
    </div>
  );
};

export default Login;
