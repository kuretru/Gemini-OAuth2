import React from 'react';
import { ProFormText } from '@ant-design/pro-form';
import type { ProColumns } from '@ant-design/pro-table';
import { FolderViewOutlined, ReloadOutlined } from '@ant-design/icons';
import { Button, Image, Modal } from 'antd';
import BasePage from '@/components/BasePage';
import OAuthApplicationService from '@/services/gemini-oauth2/oauth/application';
import ApplicationSecret from './components/ApplicationSecret';

const { confirm } = Modal;

interface OAuthApplicationState {
  applicationSecret: API.OAuth.OAuthApplicationSecretVO;
  modalVisible: boolean;
}
class OAuthApplication extends React.Component<{}, OAuthApplicationState> {
  service = new OAuthApplicationService();

  columns: ProColumns<API.OAuth.OAuthApplicationDTO>[] = [
    {
      align: 'center',
      dataIndex: 'name',
      title: '名称',
      width: 240,
      render: (text, record) => [
        <Image key="avatar" src={record.avatar} width={25} height={25} />,
        <a href={record.homepage} key="homepage" rel="noreferrer" target="_blank">
          {record.name}
        </a>,
      ],
    },
    {
      align: 'center',
      copyable: true,
      dataIndex: 'description',
      search: false,
      title: '描述',
    },
    {
      align: 'center',
      key: 'manager',
      title: '密钥管理',
      valueType: 'option',
      width: 240,
      render: (_, record) => {
        return [
          <Button
            icon={<FolderViewOutlined />}
            key="view"
            onClick={() => this.onViewButtonClick(record.id!)}
            type="primary"
          >
            查看
          </Button>,
          <Button
            icon={<ReloadOutlined />}
            key="generate"
            onClick={() => this.onManagerButtonClick(record.id!)}
          >
            重新生成
          </Button>,
        ];
      },
    },
  ];

  constructor(props: {}) {
    super(props);
    this.state = {
      applicationSecret: {
        clientId: '',
        clientSecret: '',
        secretCreateTime: '',
      },
      modalVisible: false,
    };
  }

  onViewButtonClick = (id: string) => {
    this.service.getSecret(id).then((response) => {
      this.setState({
        applicationSecret: response.data,
        modalVisible: true,
      });
    });
  };

  onManagerButtonClick = (id: string) => {
    confirm({
      title: '二次确认',
      content: '确定要重新生成密钥吗？原有密钥将会立刻失效。',
      onOk: () => {
        this.service.generateSecret(id).then((response) => {
          this.setState({
            applicationSecret: response.data,
            modalVisible: true,
          });
        });
      },
    });
  };

  onModalOk = () => {
    this.setState({ modalVisible: false });
  };

  onModalCancel = () => {
    this.setState({ modalVisible: false });
  };

  formItem = () => {
    return (
      <>
        <ProFormText
          label="应用名称"
          name="name"
          placeholder="请输入应用名称"
          rules={[{ max: 16, required: true }]}
          tooltip="最长16位"
          width="lg"
        />
        <ProFormText
          label="头像URL"
          name="avatar"
          placeholder="请输入头像URL"
          rules={[{ max: 128, required: true }]}
          tooltip="最长128位"
          width="lg"
        />
        <ProFormText
          label="描述"
          name="description"
          placeholder="请输入应用描述"
          rules={[{ max: 128, required: true }]}
          tooltip="最长128位"
          width="lg"
        />
        <ProFormText
          label="首页地址"
          name="homepage"
          placeholder="请输入首页地址"
          rules={[{ max: 64, required: true }]}
          tooltip="最长64位"
          width="lg"
        />
        <ProFormText
          label="回调地址"
          name="callback"
          placeholder="请输入回调地址"
          rules={[{ max: 64, required: true }]}
          tooltip="最长64位"
          width="lg"
        />
      </>
    );
  };

  render() {
    return (
      <>
        <BasePage<API.OAuth.OAuthApplicationDTO, API.OAuth.OAuthApplicationQuery>
          pageName="OAuth2应用"
          service={this.service}
          columns={this.columns}
          formItem={this.formItem()}
        />
        <Modal
          title="密钥查看"
          open={this.state.modalVisible}
          footer={[
            <Button onClick={this.onModalOk} type="primary">
              确定
            </Button>,
          ]}
          onCancel={this.onModalCancel}
        >
          <ApplicationSecret record={this.state.applicationSecret} />
        </Modal>
      </>
    );
  }
}

export default OAuthApplication;
