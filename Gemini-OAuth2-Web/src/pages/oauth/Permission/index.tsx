import React from 'react';
import type { ActionType, ProFormInstance } from '@ant-design/pro-components';
import { PageContainer, ProTable } from '@ant-design/pro-components';
import { ProFormText } from '@ant-design/pro-form';
import type { ProColumns } from '@ant-design/pro-table';
import { Button, Image, message, Modal } from 'antd';
import { DeleteOutlined, QuestionCircleOutlined } from '@ant-design/icons';
import OAuthPermissionService from '@/services/gemini-oauth2/oauth/permission';
import PermissionLabel from './components/PermissionLabel';

const { confirm } = Modal;

const permissionChinese = {
  "email": "电子邮箱",
  "mobile": "手机号码"
};

interface OAuthPermissionProps {

}

interface OAuthPermissionState {
  tableLoading: boolean;
}

class OAuthPermission extends React.Component<OAuthPermissionProps, OAuthPermissionState>  {
  columns: ProColumns<API.OAuth.OAuthPermissionVO>[] = [
    {
      align: 'center',
      key: 'index',
      title: '序号',
      valueType: 'indexBorder',
      width: 60,
    },
    {
      align: 'center',
      dataIndex: 'applicationName',
      search: false,
      title: '应用',
      width: 240,
      render: (_, record) => [
        <Image key="avatar" src={record.application.avatar} width={25} height={25} />,
        <a href={record.application.homepage} key="homepage" rel="noreferrer" target="_blank">
          {record.application.name}
        </a>,
      ]
    },
    {
      align: 'center',
      dataIndex: 'permissions',
      search: false,
      title: '权限',
      render: (_, record) => {
        const result: any[] = [];
        record.permissions.forEach(permission => {
          let type = "读取";
          if (permission.endsWith('_w')) {
            permission.replace('_w', '');
            type = "修改";
          }

          let name = "未知";
          for (const key in permissionChinese) {
            if (permission.startsWith(key)) {
              name = permissionChinese[key];
              break;
            }
          }

          result.push(<PermissionLabel type={type} name={name} />);
        })
        return result;
      }
    },
    {
      align: 'center',
      key: 'action',
      title: '操作',
      valueType: 'option',
      width: 240,
      render: (_, record) => {
        return [
          <Button
            danger
            icon={<DeleteOutlined />}
            key="delete"
            onClick={() => this.onDeleteButtonClick(record.id!)}
            type="primary"
          >
            取消授权
          </Button>,
        ];
      },
    },
  ];
  service: OAuthPermissionService;
  formRef: React.MutableRefObject<ProFormInstance>;
  tableRef: React.RefObject<ActionType>;

  constructor(props: Record<string, unknown>) {
    super(props);
    this.state = {
      tableLoading: false,
    };
    this.service = new OAuthPermissionService();
    this.formRef = React.createRef<ProFormInstance>() as React.MutableRefObject<ProFormInstance>;
    this.tableRef = React.createRef<ActionType>();
  }

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

  fetchData = async (params: API.PaginationQuery) => {
    return this.service
      .listByPage(params)
      .catch((error: any) => message.error(error.message));
  };

  onDeleteButtonClick = (id: string) => {
    const messageKey = 'delete';
    const _this = this;
    confirm({
      title: `确定删除这条授权记录吗？`,
      icon: <QuestionCircleOutlined />,
      okType: 'danger',
      onOk() {
        message.loading({ content: '请求处理中...', duration: 0, key: messageKey });
        _this.service
          .remove(id)
          .then(() => {
            _this.tableRef.current?.reload();
            message.success({ content: '删除成功！', key: messageKey });
          })
          .catch((error: any) => {
            message.destroy(messageKey);
            message.error(error.message);
          });
      },
    });
  };

  render() {
    return (
      <PageContainer>
        <ProTable<API.OAuth.OAuthPermissionVO>
          actionRef={this.tableRef}
          bordered
          columns={this.columns}
          headerTitle={"我授权的应用"}
          loading={this.state.tableLoading}
          options={{ fullScreen: true, setting: true }}
          pagination={{ defaultPageSize: 20 }}
          request={this.fetchData}
          rowKey="id"
          search={false}
          tooltip={"我授权的应用"}
        />
      </PageContainer>
    );
  }
}

export default OAuthPermission;
