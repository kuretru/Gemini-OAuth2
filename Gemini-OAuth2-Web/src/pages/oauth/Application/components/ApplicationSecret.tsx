import { CopyOutlined } from '@ant-design/icons';
import { Button, Descriptions, message } from 'antd';
import copy from 'copy-to-clipboard';

export type ApplicationSecretProps = {
  record: API.OAuth.OAuthApplicationSecretVO;
};

const ApplicationSecret: React.FC<ApplicationSecretProps> = (props) => {
  const secretGenerated = (record: API.OAuth.OAuthApplicationSecretVO) => {
    return record.clientSecret !== '' && record.secretCreateTime !== '1970-01-01 00:00:00';
  };

  const onCopyButtonClick = (content: string) => {
    copy(content);
    message.success('已复制');
  };

  return (
    <Descriptions bordered column={1}>
      <Descriptions.Item label="客户端ID">
        <Button icon={<CopyOutlined />} onClick={() => onCopyButtonClick(props.record.clientId)}>
          {props.record.clientId}
        </Button>
      </Descriptions.Item>
      <Descriptions.Item label="客户端密钥">
        {secretGenerated(props.record) ? (
          props.record.clientSecret.includes('****') ? (
            props.record.clientSecret
          ) : (
            <Button
              icon={<CopyOutlined />}
              onClick={() => onCopyButtonClick(props.record.clientSecret)}
            >
              {props.record.clientSecret}
            </Button>
          )
        ) : (
          '尚未生成'
        )}
      </Descriptions.Item>
      <Descriptions.Item label="生成时间">
        {secretGenerated(props.record) ? props.record.secretCreateTime : '尚未生成'}
      </Descriptions.Item>
    </Descriptions>
  );
};

export default ApplicationSecret;
